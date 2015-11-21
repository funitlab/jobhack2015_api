<?php defined('BASEPATH') OR exit('No direct script access allowed');

/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */

class User_Model extends CI_Model{
    public function __construct() {
        parent::__construct();
        
    }
    
    public function getUser($userId){
        $query = $this->db->get_where('users', array('id'=>$userId));
        return $query->row_array();
    }
}
