<?php
require('include/db.class.php');
$db = DB::getInstance();


//$_GET['token']='7364f386449f6676a8e4c9524c8bd327';
//$_GET['unite']=10;
//$_GET['idReceiver']=1;

$parameters = array
(
	':idReceiver' => null,
	':token' => null,
	':unite' => null
);

foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}

$json = array(
	'error' => true
);

$unite=(int) $parameters[':unite'];
$datesend=date("Y/m/d");
$user = $db->getMembreByToken($parameters[":token"]);
if($user !== false && (time()-$user->heure) < 2700)
{
    $credit=$user->credit;
    if($unite<=$credit and $credit>0){
        $credit=$credit-$unite;
        $membre = $db->updateMembre($credit,$user->idMembre);
	    if($membre==1){
	        $receiver = $db->getMembreById($parameters[':idReceiver']);
	        $credit=$receiver->credit;
            $credit=$credit+$unite;
	        $mem = $db->updateMembre($credit,$parameters[':idReceiver']);
	        $id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$unite);
           
        }
		
    } 
	$json = array(
		'error' => false
		
	);
}



echo json_encode($json);

	
	

	

	
	


?>