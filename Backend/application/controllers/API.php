<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class API extends CI_Controller {

	public function __construct()
	{
		parent::__construct();	
		$this->load->model([
            'home_model',
            'places_model',
            'feedback_model'
        ]);
        date_default_timezone_set('Asia/Tashkent');
	}

	/*API FUNCTIONS*/

	// error codes from 100 to 199
	public function index()
	{	 
		$this->error('WRONG_MSISDN', 101);
	}

	private function error($message, $error_code)
	{
		echo json_encode(array(
			'ok' => false, 
			'respond' => array(
				'error' => $message, 
				'error_code' => $error_code 
		    ),
			'time' => date("Y-m-d h:i:sa")
		));
	}

	private function success($data)
	{
		echo json_encode(array(
			'ok' => true, 
			'respond' => $data,
			'time' => date("Y-m-d h:i:sa")
		));
	}

	public function authCheck()
	{
		if ($this->session->userdata('authApi') == false){
			$this->error('UNAUTHORIZED', 102);
			return false;
		}
		return true;
	}

	public function run($model, $function)
	{
		//$this->load->model($model); // for optimization chech it 
		if ($this->home_model->is_user($this->session->userdata('user_id')) > 0) {
			$this->success($this->$model->$function($this->session->userdata('user_id')));
		}else{
			$this->error('USER_NOT_FOUND', 202);
		}
	}


	/*END OF API FUNCTIONS*/



	/*HOME CONTROLLER FUNCTIONS*/

	//error codes from 200 to 299

	public function login()
	{

        $data['user'] = (object)$postData = [
            'email'     => $this->input->post('email',true),
            'password'  => trim($this->input->post('password',true), " ")
        ]; 

        if ($data['user']->email == null || $data['user']->password == null) {
        	$this->error('NOT_ENOUGH_PARAMS', 201);
        }else{
        	$user = $this->home_model->check_user($postData);
        	if (count($user) > 0) {
        		$this->session->set_userdata([
        			'authApi' => true,
        			'user_id' => $user[0]['id']
        		]);
        		$this->success($user[0]);
        	}else{
        		$this->error('USER_NOT_FOUND', 202);
        	}
        }
	}

	public function logout()
	{
		$this->session->sess_destroy();
		$this->success('');
	}

	/*END OF HOME CONTROLLER FUNCTIONS*/


	/*START OF HOTELS CONTROLLER FUNCTIONS*/

	public function getPlaces()
	{
		if ($this->authCheck())
		{
			$postData = [
				'type'     => $this->input->post('type',true),
				'region'  => $this->input->post('region',true)
			];

			if ($this->home_model->is_user($this->session->userdata('user_id')) > 0) {
				$data = $this->places_model->getPlaces($postData);
				foreach ($data as $key => $value) {
					$feedback = $this->feedback_model->getFeedback(["place_id"=>$value['id']]);
					$totalRating = 0;
					foreach ($feedback as $index => $val) {
						$totalRating+=$val['sentiment_ratio'];
					}
					$data[$key]['feedback'] = $feedback;
					$data[$key]['totalRating'] = $totalRating;
				}

				$this->success($data);
			}else{
				$this->error('USER_NOT_FOUND', 202);
			}
		}
	}

	function filldatabase()
	{
		if ($this->home_model->is_user($this->session->userdata('user_id')) > 0) {
			$this->success($this->places_model->curlWrapper($this->input->get('location',true),$this->input->get('id',true)));
		}else{
			$this->error('USER_NOT_FOUND', 202);
		}
	}

	function createTripPlan()
	{
		if ($this->authCheck())
		{
			$json =  json_decode(file_get_contents('php://input'),true);
			
			$places = $this->places_model->getPlacesForTrip($json);
			$sum_cost = 0;
			$sum_time = 0;
			$cost_limit_id = 0;
			$out_limit = [];
			$result = [];
			$crossed_region = [];
			if (isset($json["time"]))
			{
				$json["time"] = ((int)$json["time"]) * 9 * 60;
			}

			if (isset($json["time"]) || isset($json["cost"]))
			{
				foreach ($places as $key => $value) 
				{	
					if (isset($json["cost"]) && isset($value["cost"]) && $sum_cost <= $json["cost"])
					{
						$sum_cost = $sum_cost + ((int)$value["cost"]) + 5;
						$sum_cost = $sum_cost + (!isset($crossed_region[$value["region_id"]]) ? 15 : 0);
					}
					else if (isset($json["cost"]))
					{
						break;
					}

					if (isset($json["time"]) && isset($value["time"]) && $sum_time <= $json["time"])
					{
						$sum_time += ((int)$value["time"]) + 15;
						$sum_time = $sum_time + (!isset($crossed_region[$value["region_id"]]) ? 320 : 0);
					}
					else if (isset($json["time"]))
					{
						break;
					}
					$crossed_region[$value["region_id"]] = true;
					array_push($result, $value);
				}
			}
			else
			{
				$result = $places;
			}

			$price = array();
			foreach ($result as $key => $row)
			{
				$price[$key] = $row['region_id'];
			}
			array_multisort($price, SORT_ASC, $result);
			echo json_encode($result);
		}
	}

	/*START OF HOTELS CONTROLLER FUNCTIONS*/


	/*START OF FEEDBACK CONTROLLER FUNCTIONS*/

	public function postFeedback()
	{
		if ($this->authCheck()) {
			$postData = [
	            'text'     => $this->input->post('text',true),
	            'feed_to_id' => $this->input->post('feed_to_id',true)
	        ];

			$this->success($this->feedback_model->postFeedback($postData));
		}
	}

	public function getFeedback()
	{
		if ($this->authCheck()) {
			$postData = [
	            'place_id'     => $this->input->post('place_id',true),
	            'user_id'   => $this->session->userdata('user_id')
	        ];
	        
			$this->success($this->feedback_model->getFeedback($postData));
		}
	}

	/*END OF FEEDBACK CONTROLLER FUNCTIONS*/




}
