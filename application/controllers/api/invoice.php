<?php

defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
require APPPATH . '/libraries/REST_Controller.php';

class Invoice extends REST_Controller {

    public function __construct() {
        parent:: __construct();
        $this->load->model('invoice_model');
        $this->load->model('user_model');
        $this->load->model('plan_model');
        $this->load->model('company_model');
        $this->load->model('connect_model');
        $this->load->model('email_model');
        $this->load->model('client_model');
    }

    // Get data of one invoice, did not support get multiple invoice yet.
    public function index_get() {
        $userId = $this->get('user_id');
        $id = $this->get('id');
        $clientId = $this->get('client_id');
                
        if ($id) {
            $response = $this->invoice_model->getInvoice($id);
        } else if ($userId) {
            $response = $this->invoice_model->getInvoiceByUser($userId);
        } else if ($clientId) {
            $response = $this->invoice_model->getInvoiceByClient($clientId);
        } else {
            $response = null;
        }

        if ($response) {
            $code = 200;
        } else {
            $code = 404;
            $response = array('error' => "404 - Not found");
        }
        $this->response($response, $code);
    }

    public function index_post() {
        $data = $this->post();
        
        $invoiceData = $this->invoice_model->createInvoice($data);

        if ($invoiceData) {
            
            // Handle the recurring option:
            $planId = $this->handleRecurring($invoiceData);
            
            $response = $invoiceData;

            $this->response($response, 201); // 201 being the HTTP response code
        } else {
            $this->response(array('error' => 'Invoice could not be created'), 404);
        }
    }

    public function index_put() {
        $id = $this->put('id');
        
        if (!$id) {
            $this->response(array('error' => '400 - An "id" must be provided by GET parameter to put.'), 400);
        } else {
            $data = $this->put();
            if ($this->invoice_model->updateInvoice($id, $data)) {
                $response = array_merge(array('user_id' => $id), $data);
                $this->response($response, 200); 
            } else {
                $this->response(array('error' => "Error"), 500);
            }
        }
    }

    function index_delete($id = "") {
        if (!$id) {
            $this->response(array('error' => '400 - An "id" must be provided by GET parameter to delete.'), 400);
        } else {
            if ($this->invoice_model->deleteInvoice($id)) {
                
                $this->response(array('id' => $id), 200);
            } else {
                // Not found
                $this->response(array('error' => '404 - Invoice could not be found'), 404);
            }
        }
    }
    
    function sendmail_post($id, $tada=null){
        // Get invoice info from data base:
        $invoice = $this->invoice_model->getInvoiceByUid($id);
        if (!$invoice){
            $this->response(array('status' => "Not found."), 404);
        }
        // Get user to get the Name of the user for Display Name in email
        $userId = $invoice['user_id'];
        $user = $this->user_model->getUser($userId);
        $company = $this->company_model->getCompany($userId);
        $client = $this->client_model->getClient($invoice['client_id']);

        $displayName = $company['name'];
        $recipient = $client['email'];
        $bcc = $user['email'] . "," . $this->config->item("email_user");

        $subject = "Invoice Payment Information";
        $message = $invoice['announce_email'];
        $result = $this->email_model->sendConfirmMailwBcc($displayName, $recipient, $bcc, $subject, $message);
        if ($result){
            $this->response(array('status' => "OK"), 200);
        } else {
            $this->response(array('status' => "Error"), 500);
        }
    
    }
    
    private function handleRecurring($data) {
        // Check if creating a fixed recurring plan.
        // Then create a new plan to Stripe:
        if ($data['frequency'] > 1) {
            // Create a plan
            $invoiceId = $data['id'];
            $amount = $this->invoice_model->getTotalAmount($invoiceId);
            $interval = $this->getInterval($data['frequency']);
            $intervalCount = $this->getIntervalCount($data['frequency']);
            $currency = 'usd';

            // Name the plan :D
            $user = $this->company_model->getCompany($data['user_id']);
            $userName = strtoupper($user['name']);
            $name = $invoiceId.". Payment to " . $userName . " $" . $amount . " every " . $intervalCount . " " . $interval;
            $planId = str_replace(" ", "_", $name);

            $plan = $this->createPlan($invoiceId, $planId, $name, $amount, $interval, $intervalCount, $currency);
            return $plan;
        }
        return false;
    }

    private function getInterval($id) {
        switch ($id) {
            case 7:
                return 'week';
            case 30:
                return 'month';
            case 3:
                return 'month';
            case 6:
                return 'month';
            case 12:
                return 'year';
        }
    }

    private function getIntervalCount($id) {
        switch ($id) {
            case 3:
                return 3;
            case 6:
                return 6;
            default :
                return 1;
        }
    }

    private function createPlan($invoiceId, $id, $name, $amount, $interval, $intervalCount, $currency) {

        // Load the library and config
        require_once(APPPATH . 'libraries/Stripe.php');
        $this->config->load('stripe');

        // set your secret key: remember to change this to your live secret key in production
        // see your keys here https://manage.stripe.com/account
        // Here we handle everything on behalf of user.
        // Get the Stripe connect
        
        // Get invoice info from data base:
        $invoice = $this->invoice_model->getInvoice($invoiceId);
        $userId = $invoice['user_id'];
        $stripeConnect = $this->connect_model->getConnect($userId);
        $userSecretKey = $stripeConnect['access_token'];

        Stripe::setApiKey($userSecretKey);
        
        try {
            Stripe_Plan::create(array(
                "amount" => $amount*100,
                "interval" => $interval,
                "interval_count" => $intervalCount,
                "name" => $name,
                "currency" => $currency,
                "id" => $id
            ));
            // After create successful:
            // Save to the database.
            $planData = array(
                "invoice_id" => $invoiceId,
                "plan_id" => $id,
                "name" => $name,
                "amount" => $amount,
                "interval_time" => $interval,
                "interval_count" => $intervalCount,
                "currency" => $currency
            );
            $planId = $this->plan_model->createPlan($planData);
        } catch (Exception $e){
            // Create plan error
            return false;
        }
        return $planId;
    }

}
