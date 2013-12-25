<?php
require('include/db.class.php');
$db = DB::getInstance();


//$_GET['token']='a916cba4fa4c1b099466e6407697988a';
//$_GET['unite']=10;
//$_GET['idReceiver']=1;
//$_GET['libelle']='virement';
//$_GET['valide']=1;


$parameters = array
(
	':idReceiver' => null,
	':token' => null,
	':unite' => null,
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

$unite=(int) $parameters[':unite'];
$datesend=date("Y/m/d");
$user = $db->getMembreByToken($parameters[":token"]);
if($user !== false && (time()-$user->heure) < 2700)
{
    if ($parameters[":valide"] == 1 ) {
    $credit=$user->credit;
        if($unite<=$credit and $credit>0){
                 $credit=$credit-$unite;
                 $membre = $db->updateMembre($credit,$user->idMembre);
	         if($membre==1){
	             $receiver = $db->getMembreById($parameters[':idReceiver']);
	             $credit=$receiver->credit;
                 $credit=$credit+$unite;
	             $mem = $db->updateMembre($credit,$parameters[':idReceiver']);
	             $id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$unite,$parameters[':libelle'],1);
                 $json = array(
		         'error' => false);
                }   
	    } else { 
		     $json = array(
		     'error' => true,
		     'message'=> "Vous avez plus de credit" );
		
	    }      
	} else {
	     $id = $db->insertCompte($user->idMembre,$parameters[':idReceiver'],$datesend,$unite,$parameters[':libelle'],0);
		 $json = array(
		 'error' => false);
	}		 
	
}



echo json_encode($json);

	
	

	

	
	


?>