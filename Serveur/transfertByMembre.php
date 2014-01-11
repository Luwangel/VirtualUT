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

	//*** Requ�te ***//
	
	$user = $db->getMembreByToken($parameters[":token"]);

	if($user !== false && (time()-$user->heure) < 2700) {
	
		//Si le virement est effectif
		if ($parameters[":valide"] == 1 ) {
			
			//Date courante du serveur
			$datesend=date("Y/m/d");

			$creditSender=$user->credit;
			
			//V�rifie le montant
			if($montant <= $creditSender and $creditSender > 0) {
				
				//Calcule le nouveau cr�dit
				$creditSender -= $montant;

				//Met � jour le cr�dit du sender
				$db->updateMembre($creditSender,$user->idMembre);
			
				//R�cup�re le receiver
				$receiver = $db->getMembreById($parameters[':idReceiver']);
					
				//Calcule le nouveau cr�dit
				$creditReceiver = $receiver->credit;
				$creditReceiver += $montant;

				//Met � jour le receiver
				$receiver = $db->updateMembre($creditReceiver,$parameters[':idReceiver']);
					
				//Ajoute une ligne � la table compte
				$db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$montant,$parameters[':libelle'],1);
					
				//D�clare qu'il n'y a pas eu d'erreur
				$json = array('error' => false);
			}
			else {
				$json = array(
				'error' => true,
				'message'=> "Vous n'avez plus de cr�dit.");
				
			}      
		}
		else {
			//Date renseign�e en param�tre
			$datesend=date("Y/m/d", $parameters[':date']);

			//Sinon on insert directement le virement (� valider plus tard)
			$id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$montant,$parameters[':libelle'],0);
			$json = array('error' => false);
		}
	}

	//Retourne le json
	echo json_encode($json);
?>