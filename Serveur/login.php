<?php
	require_once('include/db.class.php');
	$db = DB::getInstance();
	$_GET['login']='brad_d';
	$_GET['password']='1234';


	$parameters = array
	(
		':login' => null,
		':password' => null
	);

	foreach($_GET as $key => $value) {
		$parameters[":$key"] = $value;
	}

	$json = array(
		'error' => true
	);

	$user = $db->getMembreByLogin($parameters[":login"],$parameters[":password"]);
	if ($user!==false) {
		$token = md5(time() . $user->email . $user->password);
		$user->token = $token;
		if($db->updateToken($user->token,time(),$user->idMembre))
		{
			unset($user->login);
			unset($user->password);
			unset($user->heure);
			unset($user->token);
			$comptes = $db->getCompte($user->idMembre,$user->idMembre);

			foreach($comptes as $compte) {
				if ($compte->idSender == $user->idMembre) {
					unset($compte->emetteur);
					$compte->montant = '-' .($compte->montant);
					}
					else{
						unset($compte->recepteur);
						$compte->montant = '+' .($compte->montant);
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
	echo json_encode($json);

?>
