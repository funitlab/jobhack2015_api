<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Payment_Model extends CI_Model{
    
    public function __construct() {
        parent:: __construct();
    }
    
    public function getPayment($userId, $id = null){
        if ($id){
                $this->db->select('payment.*, invoice.title', FALSE);
                $this->db->where('payment.id',$id);
                $this->db->from('payment');
                $this->db->join('invoice', 'invoice.id=payment.invoice_id');
                $query = $this->db->get();
                $result = $query->row_array();
            return $result;
        } else {
            $this->db->select('payment.*, invoice.title', FALSE);
            $this->db->where('invoice.user_id', $userId);
            $this->db->from('payment');
            $this->db->join('invoice', 'invoice.id=payment.invoice_id');
            $query = $this->db->get();
            $result = $query->result_array();
            return $result;
        }
    }
    
    public function getSummary($userId){
        $data = array();
        $data['volume'] = $this->getPaymentVolume($userId);
        $data['invoice'] = $this->getInvoiceCount($userId);
        $data['recurring'] = $this->getRecurring($userId);
        $data['client'] = $this->getClientCount($userId);
        return $data;
    }
    
    function getInvoiceCount($userId){
        $this->db->from('invoice');
        $this->db->where('user_id', $userId);
        
        return $this->db->count_all_results();
    }
    
    function getClientCount($userId){
        $this->db->from('client');
        $this->db->where('user_id', $userId);
        
        return $this->db->count_all_results();
    }
    
    function getPaymentVolume($userId){
        $this->db->select_sum('amount');
        $this->db->from('payment');
        $this->db->join('invoice', 'invoice.id=payment.invoice_id');
        $this->db->where('user_id', $userId);
        $query = $this->db->get();
        
        return (int) $query->row()->amount;
    }
    function getPaymentNumber($userId){
        $this->db->from('payment');
        $this->db->join('invoice', 'invoice.id=payment.invoice_id');
        $this->db->where('user_id', $userId);
        
        return $this->db->count_all_results();
    }
    function getRecurring($userId){
        $this->db->where('user_id', $userId);
        $this->db->where('payment.frequency > ', 2);
        $this->db->from('payment');
        $this->db->join('invoice', 'invoice.id=payment.invoice_id');
        return $this->db->count_all_results();
    }
    function getPaymentCount($userId){
        $this->db->from('payment');
        $this->db->join('invoice', 'invoice.id=payment.invoice_id');
        $this->db->select('payer_email', false);
        $this->db->where('user_id', $userId);
        $this->db->distinct();
        $clientEmail = $this->db->get()->result_array();
        return count($clientEmail);
    }
    
    public function createPayment($data){
        
        $result = $this->db->insert('payment', $data);
        
        if ($result){
            return $this->db->insert_id();
        } else {
            return FALSE;
        }        
    }
    
    public function updatePayment($id, $data){
        
        $query = $this->db->get_where('payment', array('id'=>$id));
        
        if ($query->row_array()){
            $this->db->where('id', $id);
            return $this->db->update('payment', $data);
        } else {
            return $this->putPayment($id, $data);
        }
    }
    
    public function putPayment($id, $data){
        $insertData = array_merge(array('id'=>$id),$data);

        return $this->db->insert('payment', $insertData);      
    }
    
    // TODO : Catch delete constrain with foreign key.
    public function deletePayment($id){
        try {
            $result = $this->db->delete('payment', array('id'=>$id));
            if ($this->db->affected_rows() == 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }
}
