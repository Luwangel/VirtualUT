<?php
require('include/db.class.php');
$db = DB::getInstance();

//$_GET['token']='5d6aca6fa11d39b08d7411950ce14319';

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

$membre = $db->getMembreByToken($parameters[":token"]);
if($membre !== false)
{
        unset($membre->idMembre);
        unset($membre->login);
		unset($membre->password);
		unset($membre->token);	
		$json = array(
		'error' => false,
		'membre' => $membre
	     );
   
 }
  echo json_encode($json);
?>