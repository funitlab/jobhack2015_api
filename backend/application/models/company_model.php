
<?php

class Company_Model extends CI_Model{
    
    public function __construct() {
        parent:: __construct();
    }
    
    public function getCompany($userId){
        if ($userId){
            $query = $this->db->get_where('company', array('user_id'=>$userId));
            $result = $query->row_array();
            return $result;
        } else {
            $query = $this->db->get('company');
            $result = $query->result_array();
            
            return $result;
        }
    }
    
    public function createCompany($data){
        
        $result = $this->db->insert('company', $data);
        
        if ($result){
            $companyId = $this->db->insert_id();
            return $companyId;
        } else {
            return FALSE;
        }        
    }
    
    public function updateCompany($id, $data){
        
        $query = $this->db->get_where('company', array('user_id'=>$id));
        
        if ($query->row_array()){
            
            $this->db->where('user_id', $id);
            return $this->db->update('company', $data);
        } else {
            return $this->putCompany($id, $data);
        }
    }
    
    public function putCompany($id, $data){
        
        $insertData = array_merge(array('user_id'=>$id),$data);

        if ($this->db->insert('company', $insertData)){
            return $id;
        } else {
            return false;
        }
    }
    
    // TODO : Catch delete constrain with foreign key.
    public function deleteCompany($id){
        try {            
            // Delete in Company table.
            $result = $this->db->delete('company', array('user_id'=>$id));
            if ($this->db->affected_rows() === 0){
                return FALSE;
            }
            return $result;
        } catch (Exception $e){
            return FALSE;
        }
    }    
}
