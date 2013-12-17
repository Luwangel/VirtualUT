<?php
require_once('include/db.class.php');
$db = DB::getInstance();

//$_GET['token']='9a31b53dc83d26775cabb7d687e740e1';

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


$user = $db->getMembreByToken($parameters[":token"]);
if($user !== false && (time()-$user->heure) < 2700 )
{
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