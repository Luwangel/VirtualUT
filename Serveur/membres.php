<?php
require_once('include/db.class.php');
$db = DB::getInstance();

//$_GET['token']='f8e653dbdcf73b18efedc52ed1dd0ea4';

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

/* ***************************** */
/* *   AFFICHAGE DE LA LISTE   * */
/* ***************************** */
$user = $db->getMembreByToken($parameters[":token"]);
if($user !== false)
{
 $membres = $db->getListeCompleteMembre($user->idMembre);
	
	foreach($membres as $membre) { 
	   
	   unset($membre->login);
		unset($membre->password);
		unset($membre->token);	
		
	}
	$json = array(
		'error' => false,
		'membres' => $membres
	);
}
echo json_encode($json);  
	
 
	
	


?>