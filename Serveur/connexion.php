<?php
require_once('include/db.class.php');
$db = DB::getInstance();
    //$_GET['login']='brad_d';
    //$_GET['password']='1234';
  
  
      $parameters = array
      (
        ':email' => null,
        ':password' => null
      );
	  
      foreach($_GET as $key => $value)
      {
        $parameters[":$key"] = $value;
      }
	  
      $json = array(
        'error' => true
      );
  
  	    $user = $db->getMembreByLogin($parameters[":login"],$parameters[":password"]);
        if ($user!==false){
		    $token = md5(time() . $user->email . $user->password);
	        $user->token = $token;
	        if($db->updateToken($user->token,$user->idMembre))
	        {
		         $json = array(
			      'error' => false,
			      'token' => $token
		        );
	        }
	
	    }
	    echo json_encode($json);
	
?>
