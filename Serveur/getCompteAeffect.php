<?php
require('include/db.class.php');
$db = DB::getInstance();

//$_GET['token']='a916cba4fa4c1b099466e6407697988a';

$parameters = array
(
	':token' => null
);
foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}

$json = array(
	'error' => true
);

$user = $db->getMembreByToken($parameters[":token"]);
if($user !== false)
{
       
        unset($user->login);
		unset($user->password);
		unset($user->token);	
	    $comptes = $db->getCompteAeffect($user->idMembre);
	
	                 foreach($comptes as $compte) {
					    if ($compte->idSender == $user->idMembre){
					      
						   $compte->montant = '-' .($compte->montant);
						   }
						   
	                    }
				 
		         $json = array(
			      'error' => false,
				  'comptes'=>$comptes
		        );
   
 }
  echo json_encode($json);
?>