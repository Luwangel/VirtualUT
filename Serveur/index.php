
<!doctype html>

<html lang="fr">
	<head>
		<meta charset="utf-8">
		<title>IF26 - Virtual Ut</title>
		<link rel="stylesheet" href="style.css">
		<script src="script.js"></script>
	</head>
	<body>
		<h1>Virtual Ut</h1>
		<img src="virtualut.png"></img>
	<?php
		require_once('include/db.class.php');
		$db = DB::getInstance();
		
		$user = $db->getMembreByLogin("amorosad","1234");
		
		if($user !== false) {
			echo("test");
		}
		else {
			echo("pute");
		}
	?>
	</body>
</html>

