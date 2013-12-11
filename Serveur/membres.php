<?php
require_once('include/db.class.php');
$db = DB::getInstance();

$_GET['token']='01b38d3ad3fdef86464a2b81eaf5f618';

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
echo time()-$user->heure;
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