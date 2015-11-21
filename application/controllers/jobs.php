<?php

defined('BASEPATH') OR exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
require APPPATH . '/libraries/REST_Controller.php';

class Jobs extends REST_Controller {

    public function __construct() {
        parent:: __construct();
        
        $this->load->model('jobs_model');
    }

    // Get data of one company, did not support get multiple company yet.
    public function search_get($id = null) {

        if (empty($id)) {
            $result = $this->jobs_model->queryJobs();
        } else {
            $result = $this->jobs_model->queryJobs($id);
        }
        
        if (empty($result)) {
            $code = 404;
            $response = array('error' => "404 - Not found");
        } else {
            $code= 200;
            $response = $result;
        }
        $this->response($response, $code);

//        $code = 200;
//        $response = array('error' => "404 - Not found");
//        $this->response($response, $code);
    }
    
    public function test_get() {
               $code = 200;
        $response = array('OK' => "Everything is going fine");
        $this->response($response, $code); 
    }

}
