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

	$datesend=date("Y/m/d");

	//*** Requête ***//
	
	$user = $db->getMembreByToken($parameters[":token"]);

	if($user !== false && (time()-$user->heure) < 2700) {
	
		//Si le virement est effectif
		if ($parameters[":valide"] == 1 ) {

			$credit=$user->credit;
			
			//Vérifie le montant
			if($montant <= $credit and $credit > 0) {
				
				//Calcule le nouveau crédit
				$credit -= $montant;

				//Met à jour le crédit du sender
				$success = $db->updateMembre($credit,$user->idMembre);
			
				if($success == 1) {
					//Récupère le receiver
					$receiver = $db->getMembreById($parameters[':idReceiver']);
					
					//Calcule le nouveau crédit
					$credit = $receiver->credit;
					$credit += $montant;

					//Met à jour le receiver
					$receiver = $db->updateMembre($credit,$parameters[':idReceiver']);
					
					//Ajoute une ligne à la table compte
					$id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$unite,$parameters[':libelle'],1);
					
					//Déclare qu'il n'y a pas eu d'erreur
					$json = array('error' => false);
				}
				else {
					$json = array(
						'error' => true,
						'message'=> "Erreur, transaction annulée."
					);
				}
			}
			else {
				$json = array(
				'error' => true,
				'message'=> "Vous n'avez plus de crédit.");
				
			}      
		}
		else {
			//Sinon on insert directement le virement (à valider plus tard)
			$id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$montant,$parameters[':libelle'],0);
			$json = array('error' => false);
		}
	}

	//Retourne le json
	echo json_encode($json);
?>