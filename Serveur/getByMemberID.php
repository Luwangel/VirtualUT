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
	    $comptes = $db->getCompte($user->idMembre,$user->idMembre);
	
	                 foreach($comptes as $compte) {
					    if ($compte->idSender == $user->idMembre){
					       unset($compte->emetteur);
						   $compte->montant = '-' .($compte->montant);
						   }else{
						   unset($compte->recepteur);
						   $compte->montant = '+' .($compte->montant);
						   }
						   unset($compte->idSender);
	                    }
				 
		         $json = array(
			      'error' => false,
				  'membre'=> $user,
				  'comptes'=>$comptes
		        );
   
 }
  echo json_encode($json);
?>