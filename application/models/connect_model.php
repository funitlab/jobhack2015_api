
<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Connect_Model extends CI_Model{
    
    public function __construct() {
        parent:: __construct();
    }
    
    public function checkConnect($userId){
        $query = $this->db->get_where('user_stripe_connect', array('user_id'=>$userId));
        
        if ($query->row_array()){
            return TRUE;
        } else {
            return FALSE;
        }
    }
    
    public function saveConnect($userId, $data){
        $data['user_id'] = $userId;
        $result = $this->db->insert('user_stripe_connect', $data);
        if ($result){
            return $this->db->insert_id();
        } else {
            return FALSE;
        }
    }
    
    public function getConnect($userId){
        $this->db->where('user_id', $userId);
        $query = $this->db->get('user_stripe_connect');
        $result = $query->row_array();
        return $result;
    }
}
