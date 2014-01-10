<?php
	/* Fichier PHP de connexion d'un utilisateur */


	//*** Inclusion de la base de données ***//
	
	require_once('include/db.class.php');
	$db = DB::getInstance();
  
	//*** Récupération des paramètres ***//
	
	$parameters = array
	(
	':login' => null,
	':password' => null
	);

	foreach($_GET as $key => $value)
	{
	$parameters[":$key"] = $value;
	}

	$json = array(
	'error' => true
	);

	//*** Requête ***//
	
	$user = $db->getMembreByLogin($parameters[":login"],$parameters[":password"]);
	
	if ($user !== false) {
	
		//Création du token
		$token = md5(time() . $user->email . $user->password);
		$user->token = $token;
		if($db->updateToken($user->token,time(),$user->idMembre))
		{
			unset($user->login);
			unset($user->password);
			unset($user->heure);
			unset($user->token);
			
			//Récupère les 10 derniers virements de l'utilisateur qui se connecte
			$comptes = $db->getCompte($user->idMembre,$user->idMembre);

			foreach($comptes as $compte) {
				if ($compte->idSender == $user->idMembre) {
					unset($compte->emetteur);
					$compte->montant = '-' .($compte->montant);
				}
				else {
					unset($compte->recepteur);
				}
				
				unset($compte->idSender);
			}
			 
			$json = array(
				'error' => false,
				'token' => $token,
				'membre'=> $user,
				'comptes'=>$comptes
			);
		}
	}
	
	//Retourne le json
	echo json_encode($json);
	
?>
