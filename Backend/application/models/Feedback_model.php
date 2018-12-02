<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Feedback_model extends CI_Model {
 
	private $table = "feedback";
 	
 	public function getFeedback($data = [])
 	{
 		$place_id = $data['place_id'];
 		if (is_null($place_id)) {
 			$res = $this->db->query("SELECT feed_text, to_char(the_date, 'YYYY-MM-DD HH24:MI:ss') AS the_date, p.name AS title, (SELECT AVG(sentiment_ratio) AS rating FROM feedback where feed_to=p.id) as rating FROM feedback f JOIN places p ON p.id = f.feed_to WHERE f.user_id={$data['user_id']}");
 			return $res->result_array();
 		}else{
 			$res = $this->db->query("SELECT feed_text, to_char(the_date, 'YYYY-MM-DD HH24:MI:ss') AS the_date, u.name AS title, sentiment_ratio  FROM feedback f JOIN users u ON u.id = f.user_id WHERE f.feed_to={$place_id};");
 			return $res->result_array();
 		}

 		
 	}

 	public function postFeedback($data = [])
 	{

 		$data['url'] = 'sentiment';
 		$sentiment = json_decode($this->curlWrapper($data)); 
 		
 		$data['url'] = 'keyPhrases';
 		$keyPhrases = json_decode($this->curlWrapper($data)); 

 		$phrases = implode(',', $keyPhrases->documents[0]->keyPhrases);
        $phrases = "\"".str_replace(",", "\" ,\"", $phrases)."\"";

        $query = "INSERT INTO feedback(feed_text, user_id, sentiment_ratio, key_phrases, feed_to) VALUES ('{$data['text']}', {$this->session->userdata('user_id')}, {$sentiment->documents[0]->score}, '{{$phrases}}', {$data['feed_to_id']});";

 		$this->db->trans_start();

 		$res = $this->db->query($query);

 		$this->db->trans_complete();
 		return $res;
 	}


 	public function curlWrapper($data = [])
 	{
 		$text = $data['text'];

 		$ch = curl_init("https://southeastasia.api.cognitive.microsoft.com/text/analytics/v2.0/{$data['url']}");

		$send_data = "{documents:[{id:1, text: '{$text}' }]}";
		
		
		curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");
		curl_setopt($ch, CURLOPT_POSTFIELDS, $send_data);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

		curl_setopt($ch, CURLOPT_HTTPHEADER, array(
		    'Content-Type: application/json',
		    'Ocp-Apim-Subscription-Key: a7ec87a14ef849c8a0456c88f4b2faa9',
		    'Accept:application/json')
		);

		$server_output = curl_exec($ch);
		
		return $server_output;
 	}
}
