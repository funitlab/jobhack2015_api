<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Pay extends App_Controller {

    public function __construct() {
        parent::__construct();
        $this->load->model('connect_model');
        $this->load->model('invoice_model');
        $this->load->model('payment_model');
        $this->load->model('user_model');
        $this->load->model('client_model');
        $this->load->model('email_model');
        $this->load->model('company_model');
        $this->load->model('plan_model');
        $this->config->load('stripe');
        $this->config->load('email');
    }

    public function index($checkoutUid) {

        // Check for a form submission:
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {

            // Stores errors:
            $errors = array();

            // Need a payment token:
            if ($this->input->post('stripeToken')) {

                $token = $this->input->post('stripeToken');

                // Check for a duplicate submission, just in case:
                // Uses sessions, you could use a cookie instead.
                if ($this->session->userdata('token') && ($this->session->userdata('token') == $token)) {
                    $errors['token'] = 'You have apparently resubmitted the form. Please do not do that.';
                } else { // New submission.
                    $this->session->set_userdata('token', $token);
                }
            } else {
                $errors['token'] = 'The payment cannot be processed. Please make sure you have JavaScript enabled and try again.';
            }

            // Get all the parametter
            $name = $this->input->post('name');
            $email = $this->input->post('email');

            // Get Invoice info from data base:
            $invoice = $this->invoice_model->getInvoiceByUid($checkoutUid);

            if ($invoice && $invoice['public_state'] == 1) {

                // Get the Stripe connect
                $userId = $invoice['user_id'];
                $stripeConnect = $this->connect_model->getConnect($userId);
                if (!$stripeConnect) {
                    // User not connect to stripe yet
                    $errors['invoice'] = 'User have not connect to Stripe.';
                }
            } else {
                $errors['invoice'] = 'Could not find the Invoice';
            }

            // Validate other form data!
            // If no errors, process the payment:
            if (empty($errors)) {
                // create the charge on Stripe's servers - this will charge the user's card
                try {

                    // Load the library and config
                    require_once(APPPATH . 'libraries/Stripe.php');
                    $this->config->load('stripe');

                    // set your secret key: remember to change this to your live secret key in production
                    // see your keys here https://manage.stripe.com/account
                    // Here we handle everything on behalf of user.
                    $userSecretKey = $stripeConnect['access_token'];
                    Stripe::setApiKey($userSecretKey);


                    // Get the amount:
                    // Detail of the invoice
                    $amount = $this->invoice_model->getTotalAmount($invoice['id']);
                    $frequency = $invoice['frequency'];

                    // TODO : Do the frequency stuff: make it recurring...
                    if (($frequency > 1)) {
                        // Variable to check if this plan existed or not
                        $planExisted = false;

                        // Create the name of the plan. Then query to database to check if it existed.
                        // Name the plan :D
                        $user = $this->company_model->getCompany($userId);
                        $userName = strtoupper($user['name']);

                        $interval = $this->getInterval($frequency);
                        $intervalCount = $this->getIntervalCount($frequency);
                        $planName = $invoice['id'] . ". Payment to " . $userName . " $" . $amount . " every " . $intervalCount . " " . $interval;
                        $currency = "usd";
                        $planId = str_replace(" ", "_", $planName);

                        // Check from database if existed.
                        // If it was, we don't need to create new plan :)
                        // If this is fixed recurring plan, the plan should be existed. Be we don't trust it. 
                        // Because maybe the invoice change and the plan was not updated :)
//                        if ( ($invoice['amount_decide'] == 1) && (count($invoice['amounts']) == 1)){
//                            $planExisted = true;
//                        }
                        $plan = $this->plan_model->queryPlanId($planId);
                        if ($plan) {
                            $planExisted = true;
                        }
                        // Now if plan did not existed yet, create a new one:
                        if (!$planExisted) {
                            // User Stripe library to create new plan:
                            Stripe_Plan::create(array(
                                "amount" => $amount * 100,
                                "interval" => $interval,
                                "interval_count" => $intervalCount,
                                "name" => $planName,
                                "currency" => $currency,
                                "id" => $planId
                            ));
                            // After create successful:
                            // Save to the database.
                            $planData = array(
                                "invoice_id" => $invoice['id'],
                                "plan_id" => $planId,
                                "name" => $planName,
                                "amount" => $amount,
                                "interval_time" => $interval,
                                "interval_count" => $intervalCount,
                                "currency" => $currency
                            );

                            $planIdLocal = $this->plan_model->createPlan($planData);
                            // Check if error on saving plan to database
                            if (!$planIdLocal) {
                                throw new Exception;
                            }
                        }
                        // Until now, everything go fine.
                        // Subscribe user to this plan:
                        $customer = Stripe_Customer::create(array(
                                    'card' => $token,
                                    'email' => $email,
                                    'plan' => $planId,
                                    'description' => $name
                        ));
//                        var_dump($customer);
                        // After successful, just finish. Payment will be done soon later.
                        $data['confirmation'] = $invoice['confirmation_message'];
                        $data['confirmation'] .= "<p>You have just successfully created a recurring payment plan. Your payment will be made shortly.</p>";
                        $data['invoice_uid'] = $checkoutUid;
                        // Sending email here:
                        // Need to check the return of sending mail: success or not. :). Now it just show nothing.
                        // Send subscript mail instead.
                        $this->sendMailSubs($planId, $email, $name);

                        $this->render_embed_page('pay/thankyou', $data);
                    } else {
                        // One time payment
                        // Charge user
                        $charge = Stripe_Charge::create(array(
                                    "amount" => $amount * 100, // amount in cents, again
                                    "currency" => "usd",
                                    "card" => $token,
                                    "description" => $checkoutUid
                        ));
                        // Save the payment detail
                        // Check that it was paid:
                        if ($charge->paid == true) {
                            // Payment successful.
                            // Save to Payment table

                            $paymentData = array(
                                'invoice_id' => $invoice['id'],
                                'payer_name' => $name,
                                'payer_email' => $email,
                                'frequency' => $frequency,
                                'amount' => $amount
                            );
                            $paymentId = $this->payment_model->createPayment($paymentData);

                            // Set payment state of invoice to 1=paid.
                            $invoiceData = array(
                                'payment_state' => 1
                            );
                            $this->invoice_model->updateInvoiceTable($invoice['id'], $invoiceData);

                            if ($paymentId) {
                                // Save payment successful
                                $data['confirmation'] = $invoice['confirmation_message'];
                                $data['invoice_uid'] = $checkoutUid;

                                // Sending email here:
                                // FIXME: Need to check the return of sending mail: success or not. :). Now it just show nothing.
                                $this->sendmail($paymentId);

                                $this->render_embed_page('pay/thankyou', $data);
                            } else {
                                // Error on saving payment
                            }
                        } else { // Charge was not paid!	
//                            echo '<div class="alert alert-error"><h4>Payment System Error!</h4>Your payment could NOT be processed (i.e., you have not been charged) because the payment system rejected the transaction. You can try again or use another card.</div>';
                            $errors['payment'] = '<div class="alert alert-error"><h4>Payment System Error!</h4>Your payment could NOT be processed (i.e., you have not been charged) because the payment system rejected the transaction. You can try again or use another card.</div>';
                        }
                    }
                } catch (Stripe_CardError $e) {
                    // Card was declined.
                    $e_json = $e->getJsonBody();
                    $err = $e_json['error'];
                    $errors['stripe'] = $err['message'];
                } catch (Stripe_ApiConnectionError $e) {
                    // Network problem, perhaps try again.
                    $errors['stripe'] = "Network Error! Please try again.";
                } catch (Stripe_InvalidRequestError $e) {
                    // You screwed up in your programming. Shouldn't happen!
                    $errors['stripe'] = "Invalid Stripe Request";
                } catch (Stripe_ApiError $e) {
                    // Stripe's servers are down!
                    $errors['stripe'] = "Stripe Server is down";
                } catch (Stripe_CardError $e) {
                    // Something else that's not the customer's fault.
                    $errors['stripe'] = "It's our fault, not yours.";
                } catch (Exception $e) {
                    // Error on the server
                    $errors['server'] = "Some error on processing your payment.";
                }
                if (!empty($errors)) {
                    // Show error screen
                    $data['invoice_uid'] = $checkoutUid;
                    $data['error'] = '';
                    foreach ($errors as $key => $error) {
                        $data['error'] .= "<p><b>" . $key . ":</b> " . $error . "</p>";
                    }
                    $this->render_embed_page('pay/error', $data);
                }
            } else {
                // A user form submission error occurred, handled below.
//                foreach ($errors as $error){
//                    $this->session->set_flashdata('app_error', $error);
//                }
                // Show error screen
                $data['invoice_uid'] = $checkoutUid;
                $data['error'] = '';
                foreach ($errors as $key => $error) {
                    $data['error'] .= "<p><b>" . $key . ":</b> " . $error . "</p>";
                }
                $this->render_embed_page('pay/error', $data);
            }
        } else {
            // Make the normal view.
            $this->loadPayView($checkoutUid);
        }
    }

    function loadPayView($checkoutUid) {
        $this->body_class[] = 'home';
        $this->page_title = 'Payment';
        $this->current_section = 'pay';

        $this->assets_js[] = 'plugins/moment/moment.min.js';
        $this->assets_js[] = 'bootbox/bootbox.min.js';
        $this->assets_js[] = 'https://js.stripe.com/v2/';
        $this->assets_js[] = 'pay/pay.js';
        $this->assets_js[] = 'plugins/moment/moment.min.js';

        $data = array();
        $invoice = $this->invoice_model->getInvoiceByUid($checkoutUid);
        if ($invoice && $invoice['public_state'] == 1) {
            if ($invoice['payment_state'] == 0) {
                $this->showInvoiceView($invoice, $checkoutUid);
            } else {
                // Payment was paid already
                // Save payment successful
                $data['confirmation'] = $invoice['confirmation_message'];
                $data['invoice_uid'] = $checkoutUid;

                $this->render_embed_page('pay/thankyou', $data);
            }
        } else {
            // Invoice not published
            $userId = $this->ion_auth->get_user_id();
            if ($userId == $invoice['user_id']) {
                // This is the owner of this invoice, show him anyway.

                // FIXME : echo this before the HTML tag -> not good.
                echo '<div class="alert alert-info" role="alert">This invoice has not been published yet. This is just a preview.</div>';

                $this->showInvoiceView($invoice, $checkoutUid);
            } else {
                // Strange people.
                $data['invoice_id'] = $invoice['id'];
                $data['invoice_uid'] = $checkoutUid;
                $data['error'] = 'This invoice has not been published yet!';
                $this->render_embed_page('pay/error', $data);
            }
        }
    }

    private function showInvoiceView($invoice, $checkoutUid) {
        // TODO
        $userId = $invoice['user_id'];
        $stripeConnect = $this->connect_model->getConnect($userId);
        if (!$stripeConnect) {
            // User not connect to stripe yet
            // Show error screen
            $data['invoice_id'] = $invoice['id'];
            $data['invoice_uid'] = $checkoutUid;
            $data['error'] = 'User have not connect to Stripe.';
            $this->render_embed_page('pay/error', $data);
        } else {
            $userPublicKey = $stripeConnect['stripe_publishable_key'];

            $data['publish_key'] = $userPublicKey;
            $data['invoice'] = $invoice;

            // Detail of the invoice
            $data['item'] = $this->invoice_model->getInvoiceItem($invoice['id']);
            $total = 0;
            foreach ($data['item'] as $item) {
                $total += $item['total'];
            }
            $data['total'] = $total;

            $data['client'] = $this->client_model->getClient($invoice['client_id']);

            $data['company'] = $this->company_model->getCompany($userId);

            $this->render_embed_page('pay/index', $data);
        }
    }

    /*
     * Reference from: https://github.com/stripe/wilde-things/blob/master/Step6/hook.php
     */

    public function webhook() {
        try {

            // Load the library and config
            require_once(APPPATH . 'libraries/Stripe.php');
            $this->config->load('stripe');

            if ($this->input->server('REQUEST_METHOD') == 'POST') {
                $postdata = file_get_contents("php://input");
                $event = json_decode($postdata);
                if ($event->type == 'invoice.payment_succeeded') {
                    $customer_id = $event->data->object->customer;

                    // Get the Plan ID:
                    // FIXME : here we get just one element from the data.
                    $data = $event->data->object->lines->data[0];
                    $planId = $data->plan->id;
                    $plan = $this->plan_model->queryPlanId($planId);
                    // TODO : handle error here: if plan can not found.

                    $invoiceId = $plan["invoice_id"];
                    $invoice = $this->invoice_model->getInvoice($invoiceId);
                    $userId = $invoice['user_id'];
                    $stripeConnect = $this->connect_model->getConnect($userId);

                    if (!$stripeConnect) {
                        // User not connect to stripe yet
                        // FIXME : handle here
                        $errors['invoice'] = 'User have not connect to Stripe.';
                        exit("Error");
                    }
                    // Here we handle everything on behalf of user.
                    $userSecretKey = $stripeConnect['access_token'];
                    Stripe::setApiKey($userSecretKey);

                    $customer = Stripe_Customer::retrieve($customer_id);
                    //$invoice = Stripe_Invoice::retrieve($event->data->object->id);
                    //var_dump($userSecretKey);
                    //var_dump($customer);
                    //var_dump($invoice);
                    // Now save to database:
                    $clientEmail = $customer['email'];

                    $frequency = $this->getFrequency($data->plan->interval, $data->plan->interval_count);

                    $amount = $data->amount;
                    $paymentData = array(
                        'invoice_id' => $invoiceId,
                        'payer_name' => $customer['description'],
                        'payer_email' => $clientEmail,
                        'frequency' => $frequency,
                        'amount' => $amount / 100
                    );
                    $paymentId = $this->payment_model->createPayment($paymentData);

                    // Set payment state of invoice to 1=paid.
                    $invoiceData = array(
                        'payment_state' => 1
                    );
                    $this->invoice_model->updateInvoiceTable($invoice['id'], $invoiceData);

                    if ($paymentId) {
                        // Save payment successful
//                        $data['confirmation'] = $invoice['confirmation_message'];
                        // Sending email here:
                        // Need to check the return of sending mail: success or not. :). Now it just show nothing.
                        $this->sendMailRecurPayment($paymentId);

//                        $this->render_embed_page('pay/thankyou', $data);
                    } else {
                        // Error on saving payment
                    }

                    // Send confirm email
                }
            }
        } catch (Exception $e) {
            
        }
    }

    /*
     * Send succesful mail after payment completed.
     */

    private function sendmail($paymentId) {

        $payment = $this->payment_model->getPayment(null, $paymentId);
        $invoiceId = $payment['invoice_id'];

        // Get invoice info from data base:
        $invoice = $this->invoice_model->getInvoice($invoiceId);

        // Get user to get the Name of the user for Display Name in email
        $userId = $invoice['user_id'];
        $user = $this->user_model->getUser($userId);
        $company = $this->company_model->getCompany($userId);

        $displayName = $company['name'];
        $recipient = $payment['payer_email'];
        $bcc = $user['email'] . "," . $this->config->item("email_user");

        // Get the amount and format it
        $amount = round($payment['amount'] * 100) / 100;

        $subject = "Invoice Payment Receipt";
        $message = "This email confirms that " . $displayName . " has successfully charged your card $" . $amount . "";
        $message .= "\r\n\r\n";
        $message .= $invoice['confirmation_message'];
        $message .= "\r\n\r\n";
        $message .= "Invoice Details:";
        $message .= "\r\n";
        $message .= "Charge Date: " . date("M j Y g:i A", strtotime($payment['created_date'])) . "\r\n";
        $message .= "Bill To: " . $payment['payer_name'] . "\r\n";
        $message .= "Amount: $" . $amount . " USD \r\n";
        //$message .= "Last 4 Card Digits: ";
        // Send the mail:
        return $this->email_model->sendConfirmMailwBcc($displayName, $recipient, $bcc, $subject, $message);
    }

    /*
     * Send mail confirm that the subscription has been made
     */

    private function sendMailSubs($planId, $clientEmail, $clientName) {
        $plan = $this->plan_model->queryPlanId($planId);

        $invoiceId = $plan['invoice_id'];
        // Get invoice info from data base:
        $invoice = $this->invoice_model->getInvoice($invoiceId);
        // Get user to get the Name of the user for Display Name in email
        // Get user to get the Name of the user for Display Name in email
        $userId = $invoice['user_id'];
        $user = $this->user_model->getUser($userId);
        $company = $this->company_model->getCompany($userId);
        $displayName = $company['name'];

        $recipient = $clientEmail;
        $bcc = $user['email'] . "," . $this->config->item("email_user");

        // Get the amount and format it
        $amount = round($plan['amount'] * 100) / 100;

        $subject = "Recurring Invoice Subscription";
        $message = "This email confirms that you had successfully subscripted to Recurring Invoice Plan:";
        $message .= "\r\n";
        $message .= "#" . $plan['name'];
        $message .= "\r\n\r\n";
        $message .= $invoice['confirmation_message'];
        $message .= "\r\n\r\n";
        $message .= "Plan Details:";
        $message .= "\r\n";
        $message .= "Bill To: " . $clientName . "\r\n";
        $message .= "Amount: $" . $amount . " USD \r\n";
        $message .= "Recurring: Every " . $plan['interval_count'] . " " . $plan['interval_time'] . "\r\n";
        $message .= "Created Date: " . date("M j Y g:i A", strtotime($plan['created_date'])) . "\r\n";
        //$message .= "Last 4 Card Digits: ";
        // Send the mail:
        return $this->email_model->sendConfirmMailwBcc($displayName, $recipient, $bcc, $subject, $message);
    }

    /*
     * Send mail confirm that the recurring payment has been complete
     */

    private function sendMailRecurPayment($paymentId) {
        $payment = $this->payment_model->getPayment(null, $paymentId);

        $invoiceId = $payment['invoice_id'];
        // Get invoice info from data base:
        $invoice = $this->invoice_model->getInvoice($invoiceId);
        // Get user to get the Name of the user for Display Name in email
        $userId = $invoice['user_id'];
        $user = $this->user_model->getUser($userId);
        $company = $this->company_model->getCompany($userId);

        $displayName = $company['name'];
        $recipient = $payment['payer_email'];
        $bcc = $user['email'] . "," . $this->config->item("email_user");

        // Get the amount and format it
        $amount = round($payment['amount'] * 100) / 100;

        $subject = "Invoice Payment Receipt";
        $message = "This email confirms that " . $displayName . " has successfully charged your card $" . $amount . "";
        $message .= "\r\n\r\n";
        $message .= $invoice['confirmation_message'];
        $message .= "\r\n\r\n";
        $message .= "Invoice Details:";
        $message .= "\r\n";
        $message .= "Charge Date: " . date("M j Y g:i A", strtotime($payment['created_date'])) . "\r\n";
        $message .= "Bill To: " . $payment['payer_name'] . "\r\n";
        $message .= "Amount: $" . $amount . " USD \r\n";
        //$message .= "Last 4 Card Digits: ";
        // Send the mail:
        return $this->email_model->sendConfirmMailwBcc($displayName, $recipient, $bcc, $subject, $message);
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

    private function getFrequency($interval, $count) {
        if ($interval == "week" && $count == 1) {
            return 7;
        } else if ($interval == "month" && $count == 1) {
            return 30;
        } else if ($interval == "month" && $count == 3) {
            return 3;
        } else if ($interval == "month" && $count == 6) {
            return 6;
        } else if ($interval == "year" && $count == 1) {
            return 12;
        } else {
            return 1;
        }
    }

}
