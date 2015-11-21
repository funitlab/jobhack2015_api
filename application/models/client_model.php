<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Client_Model extends CI_Model{
    
    public function __construct() {
        parent::__construct();
    }
    
    public function getClient($id){
        if ($id){
            $query = $this->db->get_where('client', array('id'=>$id));
            $result = $query->row_array();
            return $result;
        } else {
            $query = $this->db->get('client');
            $result = $query->result_array();
            
            return $result;
        }
    }
    
    public function getClientByUser($userId, $order = null){
        if ($userId){
            // Combine with the payment of this client
            $this->db->where('client.user_id', $userId);
            if ($order){
                $this->db->order_by('id', 'desc');
            }
            $query = $this->db->get('client');
            
            $result = $query->result_array();
            
            // Handle with those data. Don't worry about performance. :)
            foreach ($result as $key => $client) {
                $this->db->select('sum(payment.amount) as amount');
                $this->db->from('payment');
                $this->db->join('invoice', 'invoice.id=payment.invoice_id');
                $this->db->join('client', 'client.id=invoice.client_id');
                $this->db->where('client.id',$client['id']);
                $amount = $this->db->get()->row_array()['amount'];
                if ($amount){
                    $result[$key]['amount'] = round($amount,2);
                } else {
                    $result[$key]['amount'] = 0;
                }
            }             
            
            return $result;
        } else {
            return false;
        }
    }
    
    public function createClient($data){
        
        $result = $this->db->insert('client', $data);
        
        if ($result){
            $clientId = $this->db->insert_id();
            return $clientId;
        } else {
            return FALSE;
        }        
    }
    
    public function updateClient($id, $data){
        
        $query = $this->db->get_where('client', array('id'=>$id));
        
        if ($query->row_array()){
            
            $this->db->where('id', $id);
            return $this->db->update('client', $data);
        } else {
            return $this->putClient($id, $data);
        }
    }
    
    public function putClient($id, $data){
        
        $insertData = array_merge(array('id'=>$id),$data);

        if ($this->db->insert('client', $insertData)){
            return $id;
        } else {
            return false;
        }
    }
    
    // TODO : Catch delete constrain with foreign key.
    public function deleteClient($id){
        try {            
            // Delete in Client table.
            $result = $this->db->delete('client', array('id'=>$id));
            if ($this->db->affected_rows() === 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }    
}
