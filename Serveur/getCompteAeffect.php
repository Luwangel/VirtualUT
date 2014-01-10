<?php

	/* Fichier PHP */


	//*** Inclusion de la base de données ***//
	
	require('include/db.class.php');
	$db = DB::getInstance();

	//*** Récupération des paramètres ***//
	
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

	//*** Requête ***//
	
	$user = $db->getMembreByToken($parameters[":token"]);
	
	if($user !== false) {
       
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