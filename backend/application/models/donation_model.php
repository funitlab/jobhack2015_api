
<?php

class Donation_Model extends CI_Model{
    
    public function __construct() {
        parent:: __construct();
    }
    
    public function getDonation($userId, $id = null){
        if ($id){
            // TODO : CHECK user_ID
            $idList = explode(',', $id);
            if (count($idList) > 1 ){
                $this->db->where_in('id',$idList);
                $query = $this->db->get('donations');
                $result = $query->result_array();
                
                // Get the amount data.
                // TODO : Did not get amount data in list yet. 
            } else {
                $query = $this->db->get_where('donations', array('id'=>$id));
                $result = $query->row_array();
                
                // Get the amount data.
                if($result){
                    $queryAmount = $this->db->get_where('amounts', array('donation_id'=>$id));
                    $result['amounts'] = $queryAmount->result_array();
                }
            }
            return $result;
        } else {
            $this->db->where('user_id', $userId);
            $query = $this->db->get('donations');
            $result = $query->result_array();
            // TODO : Did not get amount data in list yet. 
            
            return $result;
        }
    }
    
    public function createDonation($data){
        
        // Extract data first:
        $donationData = $this->getDonationData($data);
        
        $result = $this->db->insert('donations', $donationData);
        
        if ($result){
            $donationId = $this->db->insert_id();
            $amountData = $this->getAmountData($data);
            $this->putAmounts($donationId, $amountData);
            return $donationId;
        } else {
            return FALSE;
        }        
    }
    
    public function updateDonation($id, $data){
        
        $query = $this->db->get_where('donations', array('id'=>$id));
        
        if ($query->row_array()){
            
            $donationData = $this->getDonationData($data);
            $amountData = $this->getAmountData($data);
            $this->putAmounts($id, $amountData);
            
            $this->db->where('id', $id);
            return $this->db->update('donations', $donationData);
        } else {
            return $this->putDonation($id, $data);
        }
    }
    
    public function putDonation($id, $data){
        
        // Extract data first:
        $donationData = $this->getDonationData($data);
        $amountData = $this->getAmountData($data);
        
        $insertData = array_merge(array('id'=>$id),$donationData);

        if ($this->db->insert('donations', $insertData)){
            return $this->putAmounts($id, $amountData);
        } else {
            return false;
        }
    }
    
    // TODO : Catch delete constrain with foreign key.
    public function deleteDonation($id){
        try {
            // Delete data in amounts table
            $this->db->delete('amounts', array('donation_id'=>$id));
            
            // Delete in Donation table.
            $result = $this->db->delete('donations', array('id'=>$id));
            if ($this->db->affected_rows() == 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }
    
    //    To handle the 'amounts' table
    function putAmounts($donationId, $amounts){
        
        // Check if records whether existed or not.
        $query = $this->db->get_where('amounts', array('donation_id'=>$donationId));
        
        // If records existed
        if ($query->row()){
            // Delete the old one
            $this->db->delete('amounts', array('donation_id'=> $donationId));
        } else {
            // Do nothing
        }
        // Insert new records
        // Generate the data array to insert
        for ($i = 0; $i<count($amounts); $i++){
            $amounts[$i]['donation_id'] = $donationId;
        }
        // Just insert batch:
        return $this->db->insert_batch('amounts', $amounts);
    }
    
    // Extract the data for donation table from post data.
    function getDonationData($data){
        $donationData = $data;
        unset($donationData['amounts']);
        return $donationData;
    }
    // Extract the data for Amounts table from post data.
    function getAmountData($data){
        $amountData = json_decode($data['amounts'], true);
        return $amountData;
    } 
    
}
