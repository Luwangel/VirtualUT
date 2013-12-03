<?php
require('db.class.php');


$db = DB::getInstance();


/* ***************************** */
/* *   AFFICHAGE DE LA LISTE   * */
/* ***************************** */

   $membres = $db->getListeCompleteMembre();
	
	foreach($membres as $membre) {
	
		echo json_encode($membre);
			
	}
	
 
	
	


?>