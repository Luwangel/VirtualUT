<?php
require('db.class.php');
$db = DB::getInstance();
    //$_POST['loginMembre']='messi_m';
    //$_POST['passwordMembre']='1234';
   	if(isset($_POST['loginMembre']) and isset($_POST['passwordMembre']))
	{
	    $log=$_POST['loginMembre'];
	    $pwa=$_POST['passwordMembre'];
		$user = $db->getMembreByLogin($log,$pwa);
        if (!empty($user)){
	        $arr['StatusID'] = "1";
            $arr['MemberID'] = $user->idMembre;
            $arr['Error'] = "";
	
	    }else{
	        $arr['StatusID'] = "0";
            $arr['MemberID'] = "0";
            $arr['Error'] = "Incorrect Username and Password";
	
	    }
	    echo json_encode($arr);
	}
?>
