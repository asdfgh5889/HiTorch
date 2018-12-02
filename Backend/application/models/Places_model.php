<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Places_model extends CI_Model {
 
	private $table = "places";

	public function getPlaces($data = [])
	{
		// type: hotel, restaurant ...
		// region: Andijan, Bukhara ...

		$res = "";
		if (isset($data['type']) && !isset($data['region']))
		{
			$res = $this->db->query("SELECT * FROM {$this->table} WHERE type = '{$data['type']}';");
		}
		else if (!isset($data['type'])  && isset($data['region']))
		{
			$res = $this->db->query("SELECT * FROM {$this->table} WHERE region_id = {$data['region']};");
		}
		else if (isset($data['type'])  && isset($data['region']))
		{
			$res = $this->db->query("SELECT * FROM {$this->table} WHERE type = '{$data['type']}' AND region_id = {$data['region']};");
		}
		else
		{
			$res = $this->db->query("SELECT * FROM {$this->table} ;");
		}

		return $res->result_array();
	}

	public function getPlacesForTrip($data = [])
	{
		$res = "";
		if (!isset($data['regions']) && isset($data['types']))
		{
			$data['types'] = implode(",", $data['types']); 
			$res = $this->db->query("SELECT * FROM {$this->table} WHERE type IN ({$data['types']}) ORDER BY priority DESC;");
		}
		else if (isset($data['regions']) && !isset($data['types']) )
		{
			$data['regions'] = implode(",", $data['regions']); 
			$res = $this->db->query("SELECT * FROM {$this->table} WHERE region_id IN ({$data['regions']}) ORDER BY priority DESC;");
		}
		else if (isset($data['regions']) && isset($data['types']))
		{
			$data['regions'] = implode(",", $data['regions']); 
			$data['types'] = implode(",", $data['types']); 
			$res = $this->db->query("SELECT * FROM {$this->table} WHERE type IN ({$data['types']}) AND region_id IN ({$data['regions']}) ORDER BY priority DESC;");
		}
		else 
		{
			$res = $this->db->query("SELECT * FROM {$this->table} ORDER BY priority DESC;");
		}
		return $res->result_array();
	}
 
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

	public function curlWrapper($location, $id)
	{
		$types = [
			"3" => "museum",
			"6" => "mosque",
			"2" => "lodging",
			"14" => "bank",
			"13" => "atm",
			"9" => "park",
			"1" => "restaurant",
			"5" => "shopping_mall",
			"15" => "embassy",
			"5" => "supermarket",
			"17" => "hospital",
			"16" => "travel_agency"
		];

		foreach($types as $key => $value)
		{
			if ($location  && $id )
			{
					$ch = curl_init("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyDp_tKJZ8cpdbVKPyJKXgA98z1_6aFiSPw&radius=25000&type={$value}&location={$location}");
					curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "GET");
					curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
					curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
				   $server_output = json_decode(curl_exec($ch));
				   if (($results = $server_output->results) )
				   {
					   file_put_contents("{$id}_{$value}.json", json_encode($server_output));
					   if (is_array($results))
					   {
						   foreach($results as $resultKey => $resultVal)
						   {
							   $place_location = "{$resultVal->geometry->location->lat},{$resultVal->geometry->location->lng}";
							   $place_name = pg_escape_string($resultVal->name);
								  $this->db->query("INSERT INTO {$this->table} (type, location, name, time, priority, region_id)
								  VALUES ({$key}, POINT({$place_location}), '{$place_name}', 30, 2, {$id});");
						   }
					   }
				   }
			}
		}
	}
}
