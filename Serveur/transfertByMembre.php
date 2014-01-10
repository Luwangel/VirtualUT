<?php
	/* Fichier PHP de transfert d'argent envers un membre */


	//*** Inclusion de la base de donn�es ***//
	
	require('include/db.class.php');
	$db = DB::getInstance();

	//*** R�cup�ration des param�tres ***//

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

	//*** Requ�te ***//
	
	$user = $db->getMembreByToken($parameters[":token"]);

	if($user !== false && (time()-$user->heure) < 2700) {
	
		//Si le virement est effectif
		if ($parameters[":valide"] == 1 ) {

			$credit=$user->credit;
			
			//V�rifie le montant
			if($montant <= $credit and $credit > 0) {
				
				//Calcule le nouveau cr�dit
				$credit -= $montant;

				//Met � jour le cr�dit du sender
				$success = $db->updateMembre($credit,$user->idMembre);
			
				if($success == 1) {
					//R�cup�re le receiver
					$receiver = $db->getMembreById($parameters[':idReceiver']);
					
					//Calcule le nouveau cr�dit
					$credit = $receiver->credit;
					$credit += $montant;

					//Met � jour le receiver
					$receiver = $db->updateMembre($credit,$parameters[':idReceiver']);
					
					//Ajoute une ligne � la table compte
					$id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$unite,$parameters[':libelle'],1);
					
					//D�clare qu'il n'y a pas eu d'erreur
					$json = array('error' => false);
				}
				else {
					$json = array(
						'error' => true,
						'message'=> "Erreur, transaction annul�e."
					);
				}
			}
			else {
				$json = array(
				'error' => true,
				'message'=> "Vous n'avez plus de cr�dit.");
				
			}      
		}
		else {
			//Sinon on insert directement le virement (� valider plus tard)
			$id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$montant,$parameters[':libelle'],0);
			$json = array('error' => false);
		}
	}

	//Retourne le json
	echo json_encode($json);
?>