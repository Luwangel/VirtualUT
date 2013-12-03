<?php		
	class DB
	{
		private static $instance;
		private $pdo;
		
		//Prepared requests
		
		private $getListeMembreComplete;
	    private $getMembreById;
		private $getMembreByLogin;
		private $updateMembre;
		private $insertCompte;
		
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
			
			$this->getListeCompleteMembre = "SELECT * FROM membre ORDER BY nom, prenom";
			$this->getMembreById = "SELECT * FROM membre WHERE idMembre=:idMembre";
            $this->getMembreByLogin = "SELECT * FROM membre WHERE login=:login AND password=:password";
			
			// GESTION DES MEMBRES : UPDATE MEMBRE
			$this->updateMembre = "UPDATE membre SET credit=:credit WHERE idMembre=:idMembre";
			
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
		
	
		public function getListeCompleteMembre()
		{
			$statement = $this->pdo->prepare($this->getListeCompleteMembre);
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
			
		public function updateMembre($credit,$idMembre)
		{
			$statement = $this->pdo->prepare($this->updateMembre);
			$statement->bindParam(':credit', $credit, PDO::PARAM_INT);
			$statement->bindParam(':idMembre', $idMembre, PDO::PARAM_INT);
			$statement->execute();
				if($statement){
            $result=1;
           } else {
            $result=0;
			}
			return $result;
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
