<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Order_Model extends CI_Model{
    
    public function __construct() {
        parent:: __construct();
    }
    
    public function getOrder($userId, $id = null){
        if ($id){
            // TODO : check userID here. NOT CHECKED YET.
            $idList = explode(',', $id);
            if (count($idList) > 1 ){
                $this->db->select('orders.*, donations.title', FALSE);
                $this->db->where_in('orders.id',$idList);
                $this->db->from('orders');
                $this->db->join('donations', 'donations.id=orders.donation_id');
                $query = $this->db->get();
                $result = $query->result_array();
            } else {
                $this->db->select('orders.*, donations.title', FALSE);
                $this->db->where('orders.id',$id);
                $this->db->from('orders');
                $this->db->join('donations', 'donations.id=orders.donation_id');
                $query = $this->db->get();
                $result = $query->row_array();
            }
            return $result;
        } else {
            $this->db->select('orders.*, donations.title', FALSE);
            $this->db->where('orders.user_id', $userId);
            $this->db->from('orders');
            $this->db->join('donations', 'donations.id=orders.donation_id');
            $query = $this->db->get();
            $result = $query->result_array();
            return $result;
        }
    }
    
    public function getSummary($userId){
        $data = array();
        $data['volume'] = $this->getDonationVolume($userId);
        $data['payment'] = $this->getPaymentNumber($userId);
        $data['recurring'] = $this->getRecurring($userId);
        $data['donor'] = $this->getDonorNumber($userId);
        return $data;
    }
    
    function getDonationVolume($userId){
        $this->db->select_sum('amount');
        $this->db->where('user_id', $userId);
        $query = $this->db->get('orders');
        
        return (int) $query->row()->amount;
    }
    function getPaymentNumber($userId){
        $this->db->where('user_id', $userId);
        $this->db->from('orders');
        return $this->db->count_all_results();
        
    }
    function getRecurring($userId){
        $this->db->where('user_id', $userId);
        $this->db->where('frequency > ', 2);
        $this->db->from('orders');
        return $this->db->count_all_results();
    }
    function getDonorNumber($userId){
        $this->db->select('client_email', false);
        $this->db->where('user_id', $userId);
        $this->db->distinct();
        $clientEmail = $this->db->get('orders')->result_array();
        return count($clientEmail);
    }
    
    public function createOrder($data){
        
        $result = $this->db->insert('orders', $data);
        
        if ($result){
            return $this->db->insert_id();
        } else {
            return FALSE;
        }        
    }
    
    public function updateOrder($id, $data){
        
        $query = $this->db->get_where('orders', array('id'=>$id));
        
        if ($query->row_array()){
            $this->db->where('id', $id);
            return $this->db->update('orders', $data);
        } else {
            return $this->putOrder($id, $data);
        }
    }
    
    public function putOrder($id, $data){
        $insertData = array_merge(array('id'=>$id),$data);

        return $this->db->insert('orders', $insertData);      
    }
    
    // TODO : Catch delete constrain with foreign key.
    public function deleteOrder($id){
        try {
            $result = $this->db->delete('orders', array('id'=>$id));
            if ($this->db->affected_rows() == 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }
}
