<?php
	/* Fichier PHP de transfert d'argent envers un membre */


	//*** Inclusion de la base de données ***//
	
	require('include/db.class.php');
	$db = DB::getInstance();

	//*** Récupération des paramètres ***//

	$parameters = array
	(
		':token' => null,
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
		$comptes = $db->getCompteAeffect($user->idMembre);
		 
		$json = array(
			'error' => false,
			'comptes' => $comptes
		);
	}
	
	//Retourne le json
	echo json_encode($json);
?>