<?php
require('db.class.php');
$db = DB::getInstance();


$_POST['idSender']=11;
$_POST['unite']=2;
$_POST['idReceiver']=10;

$idSender=$_POST['idSender'];
$idReceiver=$_POST['idReceiver'];
$unite=$_POST['unite'];
$datesend=date("Y/m/d");

$membre = $db->getMembreById($idSender);
$credit=$membre->credit;
    if($unite<=$credit and $credit>0){
        $credit=$credit-$unite;
        $membre = $db->updateMembre($credit,$idSender);
	    if($membre==1){
	        $receiver = $db->getMembreById($idReceiver);
	        $credit=$receiver->credit;
            $credit=$credit+$unite;
	        $mem = $db->updateMembre($credit,$idReceiver);
	        $id = $db->insertCompte($idSender,$idReceiver,$datesend,$unite);
            $arr['StatusID'] = "1";
            $arr['Error'] = "data saved";
    
        }
		else{
            $arr['StatusID'] = "0";
            $arr['Error'] = "Cannot save data!";  
        }
}   else{
        $arr['StatusID'] = "0";
        $arr['Error'] = "Echec transfert";  
    }

echo json_encode($arr);

	
	

	

	
	


?>