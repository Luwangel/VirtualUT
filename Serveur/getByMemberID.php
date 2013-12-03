<?php
require('db.class.php');
$db = DB::getInstance();

$_POST["MemberID"]=11;
$MemberID = $_POST["MemberID"];

   $membre = $db->getMembreById($MemberID);
	if($membre){
	$arr["MemberID"] = $membre->idMembre;
    $arr["Nom"] = $membre->nom;
	$arr["Prenom"] = $membre->prenom;
	$arr["Email"] = $membre->email;
   }
  echo json_encode($arr);
?>