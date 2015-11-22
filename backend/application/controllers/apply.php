<?php

defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
require APPPATH . '/libraries/REST_Controller.php';

class Apply extends REST_Controller {

    public function __construct() {
        parent:: __construct();
        
        $this->load->model('jobs_model');
    }
    
    public function index_get() {
        $id = $this->get('id');
        if ($id) {
            // Get a specific pin
            $result = $this->jobs_model->getJobById($id);
        } else {
            // Get list of pin
            $result = $this->jobs_model->getJobById();
        }
        
        if ($result) {
            $code = 200;
            $response =  $result;
            $this->response($response, $code); 
        } else {
            $code = 404;
            $response = array('Error' => "Something we don't know happend. Please contact hung@funitlab.com to fix it.");
            $this->response($response, $code); 
        }
    }

    public function index_post() {

        $data = $this->post()[0];
        
//        var_dump($data);
        
        $responseArray = (array) json_decode($data, true);
        
        $result = $this->jobs_model->addJob($responseArray);
        
        if ($result) {
            $code = 201;
            $response = array('id' => $result);
            $this->response($response, $code); 
        } else {
            $code = 500;
            $response = array('Error' => "Something we don't know happend. Please contact hung@funitlab.com to fix it.");
            $this->response($response, $code); 
        }
    }
    
    public function index_delete() {
        $id = $this->get('id');
        if (!$id) {
            $this->response(array('error' => '400 - An "id" must be provided by GET parameter to delete.'), 400);
        } else {
            if ($this->jobs_model->deleteJob($id)) {
                
                $this->response(array('id' => $id), 204);
            } else {
                // Not found
                $this->response(array('error' => '404 - Invoice could not be found'), 404);
            }
        }
    }
    
    
     
    public function test_get() {
               $code = 200;
        $response = array('OK' => "Everything is going fine");
        $this->response($response, $code); 
    }

}
