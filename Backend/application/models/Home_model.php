<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Home_model extends CI_Model {
 
	private $table = "users";
 
	public function check_user($data = [])
	{
		$email = pg_escape_string($data['email']);
		$password = pg_escape_string($data['password']);
		$res = $this->db->query("SELECT id, name FROM users WHERE email='{$email}' AND password=crypt('{$password}', password) LIMIT 1;");
		return $res->result_array();
	} 

	public function is_user($id)
	{
		$id = pg_escape_string($id);
		$res = $this->db->query("SELECT id FROM users WHERE id = {$id} LIMIT 1;");
		$res->result_array();
		return $res->num_rows();
	}
}
