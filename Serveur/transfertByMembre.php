<?php
	/* Fichier PHP de transfert d'argent envers un membre */


	//*** Inclusion de la base de données ***//
	
	require('include/db.class.php');
	$db = DB::getInstance();

	//*** Récupération des paramètres ***//

	$parameters = array
	(
		':idReceiver' => null,
		':token' => null,
		':montant' => null,
		':date' => null,
		':libelle'=>null,
		':valide'=>null
	);

	foreach($_GET as $key => $value)
	{
		$parameters[":$key"] = $value;
	}

	$json = array(
		'error' => true
	);
	
	$montant = (int) $parameters[':montant'];

	//*** Requête ***//
	
	$user = $db->getMembreByToken($parameters[":token"]);

	if($user !== false && (time()-$user->heure) < 2700) {
	
		//Si le virement est effectif
		if ($parameters[":valide"] == 1 ) {
			
			//Date courante du serveur
			$datesend=date("Y/m/d");

			$creditSender=$user->credit;
			
			//Vérifie le montant
			if($montant <= $creditSender and $creditSender > 0) {
				
				//Calcule le nouveau crédit
				$creditSender -= $montant;

				//Met à jour le crédit du sender
				$db->updateMembre($creditSender,$user->idMembre);
			
				//Récupère le receiver
				$receiver = $db->getMembreById($parameters[':idReceiver']);
					
				//Calcule le nouveau crédit
				$creditReceiver = $receiver->credit;
				$creditReceiver += $montant;

				//Met à jour le receiver
				$receiver = $db->updateMembre($creditReceiver,$parameters[':idReceiver']);
					
				//Ajoute une ligne à la table compte
				$db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$montant,$parameters[':libelle'],1);
					
				//Déclare qu'il n'y a pas eu d'erreur
				$json = array('error' => false);
			}
			else {
				$json = array(
				'error' => true,
				'message'=> "Vous n'avez plus de crédit.");
				
			}      
		}
		else {
			//Date renseignée en paramètre
			$datesend=date("Y/m/d", $parameters[':date']);

			//Sinon on insert directement le virement (à valider plus tard)
			$id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$montant,$parameters[':libelle'],0);
			$json = array('error' => false);
		}
	}

	//Retourne le json
	echo json_encode($json);
?>