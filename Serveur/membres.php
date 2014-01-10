<?php
	/* Fichier PHP de récupération de tous les membres */


	//*** Inclusion de la base de données ***//
	
	require_once('include/db.class.php');
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

	if($user !== false && (time()-$user->heure) < 2700 ) {
		
		$membres = $db->getListeCompleteMembre($user->idMembre);
	
		foreach($membres as $membre) { 
		   
			unset($membre->login);
			unset($membre->password);
			unset($membre->token);	
			unset($membre->heure);
		}
		
		$json = array(
			'error' => false,
			'membres' => $membres
		);
	}

	echo json_encode($json);

?>