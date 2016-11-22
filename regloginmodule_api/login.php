<?php

require_once 'include/DB_Functions.php';
$db = new DB_Functions();

//JSON response array
$response = array("error" => FALSE);
if(isset($_POST['email']) && isset($_POST['password'])){

	//receiving the post parameters
	$email = $_POST['email'];
	$password = $_POST['password'];

	//get the user by email and passwors
	$user = $db->getUser($email, $password);

	if($user != false){
		//user is found
		$response["error"] = FALSE;
		$response["uid"] = $user["unique_id"];
		$response["user"]["name"] = $user["name"];
		$response["user"]["email"] = $user["email"];
		$response["user"]["created_at"] = $user["created_at"];
		$response["user"]["updated_at"] = $user["updated_at"];
		echo json_encode($response);
	}else{
		//user is not found with the credentials
		$response["error"] = TRUE;
		$response["error_msg"] = "Login credentials are wrong. Please try again!";
		echo json_encode($response);
	}

}else{
	//required post parameters are missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameter (email or password) is missing!";
	echo json_encode($response);
}

?>