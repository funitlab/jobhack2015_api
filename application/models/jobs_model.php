<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Jobs_Model extends CI_Model{
    
    public function __construct() {
        parent::__construct();
    }
    
    public function queryJobs($stringQuery = null){
        
        $apiKey = '6ff124a0f3ec81aa89eaa534f856fcac6761f6366f619a2e0dc2150485ea829d';
        $apiHost = 'https://api-staging.vietnamworks.com';
        $apiPath = '/jobs/search';
        $jsonString = json_encode(array(
            'job_title' => $stringQuery,
            'page_size' => 100
        ));
        $ch = curl_init();
        curl_setopt_array($ch, array(
            CURLOPT_URL => $apiHost . $apiPath,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_SSL_VERIFYPEER => false, // ignore SSL verification
            CURLOPT_POST => true, // http request method is POST
            CURLOPT_HTTPHEADER => array(
                "CONTENT-MD5: $apiKey",
                'Content-Type: application/json',
                'Content-Length: ' . strlen($jsonString)
            ),
            CURLOPT_POSTFIELDS => $jsonString
        ));
        $response = curl_exec($ch);
        $responseArray = (array) json_decode($response, true);       
        
        $result = null;
        if ($responseArray['meta']['code'] == 400) { // error happened
            echo 'Server returned an error with message: ' . $responseArray['meta']['message'];
        } elseif ($responseArray['meta']['code'] == 200) {
//            echo "Response status: " . $responseArray['meta']['message'] . "<br />\nJob Information: <br />";
            // search OK. Handle your own code here with $responseArray['data']
//            var_dump($responseArray['data']);
            
            // Get the list of job
            if ($responseArray['data']['total'] > 0) {
                $jobsList = $responseArray['data']['jobs'];
                $result = $this->getJobList($jobsList);
            }
            
        } else {
            //unknown error
            $info = curl_getinfo($ch);
            echo "An error happened: \n" . curl_error($ch) . "\nInformation: " . print_r($info, true) . "\nResponse: $response";
        }
        curl_close($ch);
        
        return $result;
    }
    
    public function getJobById($id = null) {
        if ($id) {
            $query = $this->db->get_where('jobs', array('id' => $id));
            $result = $query->row_array();
            
            // Convert the skills, benefits string into array
            if ($result) {
                $result['skills'] = explode(";", $result['skills']);
                $result['benefits'] = explode(";", $result['benefits']);
            }
            return $result;
        } else {
            $query = $this->db->get('jobs');
            $result = $query->result_array();

            return $result;
        }
    }
    
    private function getJobList ($vnwJobList) {
        $result = array();
        foreach ($vnwJobList as $job) {
            $item = array();
            $item['job_id'] = $job['job_id'];
            $item['job_company'] = $job['job_company'];
            $item['job_title'] = $job['job_title'];
            $item['salary_min'] = $job['salary'];
            $item['salary_max'] = $job['salary_max'];
            $item['job_detail_url'] = $job['job_detail_url'];
            $item['job_logo_url'] = $job['job_logo_url'];
            
            $item['skills'] = array();
            foreach ($job['skills'] as $skill) {
                $item['skills'][] = $skill['skillName'];
            }
            
            $item['benefits'] = array();
            foreach ($job['benefits'] as $benefit) {
                $item['benefits'][] = $benefit['benefitValue'];
            }
            
            // TODO : make description
            $item['job_description'] = $job['job_company'].' is searching for '.$job['job_title']
                    .' with competitive salary, good working environment and nice fellows. Let\'s join us if you are good at ';
            foreach ($job['skills'] as $skill) {
                $item['job_description'] .= $skill['skillName'] . ', ';
            }
            $item['job_description'] .= 'and see you there.';
            
            $result[] = $item;
        }
        
        return $result;
    }
    
    public function addJob($data){
        
        // Clean data
        $skills = $data['skills'];
        $data['skills'] = implode(";", $skills);
        
        $benefits = $data['benefits'];
        $data['benefits'] = implode(";", $benefits);
        
        $result = $this->db->insert('jobs', $data);
        
        if ($result){
            $companyId = $this->db->insert_id();
            return $companyId;
        } else {
            return FALSE;
        }        
    }
    
    // TODO
    public function updateCompany($id, $data){
        
        $query = $this->db->get_where('company', array('user_id'=>$id));
        
        if ($query->row_array()){
            
            $this->db->where('user_id', $id);
            return $this->db->update('company', $data);
        } else {
            return $this->putCompany($id, $data);
        }
    }
    
    // TODO
    public function putCompany($id, $data){
        
        $insertData = array_merge(array('user_id'=>$id),$data);

        if ($this->db->insert('company', $insertData)){
            return $id;
        } else {
            return false;
        }
    }
    
    // TODO : Catch delete constrain with foreign key.
    public function deleteJob($id){
        echo $id;
        try {            
            // Delete in jobs table.
            $result = $this->db->delete('jobs', array('id'=>$id));
            if ($this->db->affected_rows() === 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }
   
}
