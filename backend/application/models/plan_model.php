<?php defined('BASEPATH') OR exit('No direct script access allowed');

/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */
class Plan_Model extends CI_Model{
    
    public function __construct() {
        parent::__construct();
        
    }
    
    public function getPlan($id = null){
        if ($id){
            $query = $this->db->get_where('stripe_plan', array('id'=>$id));
            $result = $query->row_array();
            
            return $result;
        } else {
            return false;
        }
    }
    
    public function queryPlanId($word){
        if ($word){
            $query = $this->db->get_where('stripe_plan', array('plan_id'=>$word));
            $result = $query->row_array();
           
            return $result;
        } else {
            return false;
        }
    }
    public function queryPlanByInvoice($invoiceId){
        if ($invoiceId){
            $query = $this->db->get_where('stripe_plan', array('invoice_id'=>$invoiceId));
            $result = $query->row_array();
           
            return $result;
        } else {
            return false;
        }
    }
    
    public function createPlan($data){
        
        $result = $this->db->insert('stripe_plan', $data);
        
        if ($result){
            return $this->db->insert_id();
        } else {
            return FALSE;
        }        
    }
    
    public function updatePlan($id, $data){
        
        $query = $this->db->get_where('stripe_plan', array('id'=>$id));
        
        if ($query->row_array()){
            $this->db->where('id', $id);
            return $this->db->update('stripe_plan', $data);
        } else {
            return $this->putWord($id, $data);
        }
    }
    
    public function putPlan($id, $data){
        $insertData = array_merge(array('id'=>$id),$data);

        return $this->db->insert('stripe_plan', $insertData);      
    }
    
    // TODO : Catch delete constrain with foreign key.
    public function deletePlan($id){
        try {
            $result = $this->db->delete('stripe_plan', array('id'=>$id));
            if ($this->db->affected_rows() == 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }
    public function deletePlanByInvoice($invoiceId){
        try {
            $result = $this->db->delete('stripe_plan', array('invoice_id'=>$invoiceId));
            if ($this->db->affected_rows() == 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }
}

