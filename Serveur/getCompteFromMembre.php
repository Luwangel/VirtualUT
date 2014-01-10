<?php

	/* Fichier PHP de connexion d'un utilisateur */


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
	
	if($user !== false && (time()-$user->heure) < 2700 )
	{
	
		//Supprime des données sensibles
		unset($user->login);
		unset($user->password);
		unset($user->token);
		
		$comptes = $db->getCompte($user->idMembre,$user->idMembre);
	
		foreach($comptes as $compte) {
		
			if ($compte->idSender == $user->idMembre) { //Si emetteur
				unset($compte->emetteur);
				$compte->montant = '-' .($compte->montant);
			}
			else { //Si recepteur
				unset($compte->recepteur);
				$compte->montant = '+' .($compte->montant);
			}
			
			unset($compte->idSender);
		}
		
		//Retourne la liste des virements
		$json = array(
			'error' => false,
			'membre'=> $user,
			'comptes'=>$comptes
		);
	}
	
	echo json_encode($json);
?>