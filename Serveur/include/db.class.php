<?php		
	class DB
	{
		private static $instance;
		private $pdo;
		
		//Prepared requests
		
		private $getListeMembreComplete;
	    private $getMembreById;
		private $getMembreByLogin;
		private $getMembreByToken;
		private $updateMembre;
		private $insertCompte;
		private $updateToken;
		
		private function __construct()
		{
			try
			{
				$this->pdo = new PDO("mysql:dbname=virtualut;host=127.0.0.1", "root", "");
			}
			catch(PDOException $ex)
			{
				echo "Could not log in to database.";
			}
			
			// MEMBRE : GET
			
			$this->getListeCompleteMembre = "SELECT * FROM membre WHERE idMembre!=:idMembre ORDER BY nom, prenom";
			$this->getMembreById = "SELECT * FROM membre WHERE idMembre=:idMembre";
            $this->getMembreByLogin = "SELECT * FROM membre WHERE login=:login AND password=:password";
			$this->getMembreByToken = "SELECT * FROM membre WHERE token=:token";
			// GESTION DES MEMBRES : UPDATE MEMBRE
			$this->updateMembre = "UPDATE membre SET credit=:credit,token=:token WHERE idMembre=:idMembre";
			$this->updateToken = "UPDATE membre SET token=:token WHERE idMembre=:idMembre";
			// GESTION DES COMPTE : INSERT COMPTE
			$this->insertCompte = "INSERT INTO compte(idSender, idReceiver, date, montant) VALUES(:idSender, :idReceiver, :date, :montant)";

			
		}
		
		public static function getInstance()
		{
			if(self::$instance == null)
			{
				self::$instance = new DB();
			}
			return self::$instance;
		}
		
		
		// ==============================
		//  Membre
		// ==============================
		
	
		public function getListeCompleteMembre($idMembre)
		{
			$statement = $this->pdo->prepare($this->getListeCompleteMembre);
			$statement->bindParam(':idMembre', $idMembre, PDO::PARAM_INT);
			$statement->execute();
			$result = $statement->fetchAll(PDO::FETCH_OBJ);
			return $result;
		}
		
		public function getMembreById($idMembre)
		{						
			$statement = $this->pdo->prepare($this->getMembreById);
			$statement->bindParam(':idMembre', $idMembre, PDO::PARAM_INT);
			$statement->execute();
			
			$result = $statement->fetch(PDO::FETCH_OBJ);
			return $result;
		}
		
		public function getMembreByLogin($login,$password)
		{						
			$statement = $this->pdo->prepare($this->getMembreByLogin);
			$statement->bindParam(':login', $login, PDO::PARAM_STR);
			$statement->bindParam(':password', $password, PDO::PARAM_STR);
			$statement->execute();
			
			$result = $statement->fetch(PDO::FETCH_OBJ);
			return $result;
		}
		
		public function getMembreByToken($token)
		{						
			$statement = $this->pdo->prepare($this->getMembreByToken);
			$statement->bindParam(':token', $token, PDO::PARAM_STR);
			$statement->execute();
			
			$result = $statement->fetch(PDO::FETCH_OBJ);
			return $result;
		}
		
		public function updateMembre($credit,$token,$idMembre)
		{
			$statement = $this->pdo->prepare($this->updateMembre);
			$statement->bindParam(':credit', $credit, PDO::PARAM_INT);
			$statement->bindParam(':token', $token, PDO::PARAM_STR);
			$statement->bindParam(':idMembre', $idMembre, PDO::PARAM_INT);
			return $statement->execute();
				/*if($statement){
            $result=1;
           } else {
            $result=0;
			}
			return $result;*/
		}
		
			public function updateToken($token,$idMembre)
		{
			$statement = $this->pdo->prepare($this->updateToken);
			$statement->bindParam(':token', $token, PDO::PARAM_STR);
			$statement->bindParam(':idMembre', $idMembre, PDO::PARAM_INT);
			return $statement->execute();
			
		}
		
		// ==============================
		//  Compte
		// ==============================
			public function insertCompte($idSender, $idReceiver, $date, $montant)
		{
			$statement = $this->pdo->prepare($this->insertCompte);
			$statement->bindParam(':idSender', $idSender, PDO::PARAM_INT);
			$statement->bindParam(':idReceiver', $idReceiver, PDO::PARAM_INT);
			$statement->bindParam(':date', $date, PDO::PARAM_INT);
			$statement->bindParam(':montant', $montant, PDO::PARAM_INT);
			$statement->execute();
		}
	
	}
?>
