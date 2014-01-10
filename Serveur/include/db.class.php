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
		private $getCompte;
		private $getCompteAeffect;
		
		private function __construct()
		{
			try
			{
				$this->pdo = new PDO("mysql:dbname=amoros;host=127.0.0.1", "amoros", "amorosiut");
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
			$this->updateMembre = "UPDATE membre SET credit=:credit WHERE idMembre=:idMembre";
			$this->updateToken = "UPDATE membre SET token=:token, heure=:heure WHERE idMembre=:idMembre";
			
			// GESTION DES COMPTE : INSERT COMPTE
			$this->insertCompte = "INSERT INTO compte(idSender, idReceiver, date, montant,libelle,valide) VALUES(:idSender, :idReceiver, :date, :montant,:libelle,:valide)";
            $this->getCompte="SELECT idSender,libelle, date,montant,concat(e.nom ,' ',e.prenom) AS emetteur,concat(r.nom ,' ',r.prenom) AS recepteur
            FROM compte LEFT JOIN membre AS e ON compte.idSender = e.idMembre LEFT JOIN membre AS r ON compte.idReceiver = r.idMembre
            WHERE (idSender =:idSender OR idReceiver =:idReceiver) AND valide = 1 ORDER BY DATE DESC LIMIT 10";
			$this->getCompteAeffect="SELECT idSender,idReceiver,libelle, date,montant,concat(r.nom ,' ',r.prenom) AS recepteur
            FROM compte LEFT JOIN membre AS r ON compte.idReceiver = r.idMembre WHERE idSender =:idSender  AND valide = 0 ORDER BY DATE DESC ";
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
			$mdp = $this->hashage($password);
			
			$statement = $this->pdo->prepare($this->getMembreByLogin);
			$statement->bindParam(':login', $login, PDO::PARAM_STR);
			$statement->bindParam(':password', $mdp, PDO::PARAM_STR);
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
		
		public function updateMembre($credit,$idMembre)
		{
			$statement = $this->pdo->prepare($this->updateMembre);
			$statement->bindParam(':credit', $credit, PDO::PARAM_INT);
			$statement->bindParam(':idMembre', $idMembre, PDO::PARAM_INT);
			return $statement->execute();
			
		}
		
		public function updateToken($token,$heure,$idMembre)
		{
			$statement = $this->pdo->prepare($this->updateToken);
			$statement->bindParam(':token', $token, PDO::PARAM_STR);
			$statement->bindParam(':heure', $heure, PDO::PARAM_STR);
			$statement->bindParam(':idMembre', $idMembre, PDO::PARAM_INT);
			return $statement->execute();
			
		}
		
		// ==============================
		//  Compte
		// ==============================
		
		public function insertCompte($idSender, $idReceiver, $date, $montant,$libelle,$valide)
		{
			$statement = $this->pdo->prepare($this->insertCompte);
			$statement->bindParam(':idSender', $idSender, PDO::PARAM_INT);
			$statement->bindParam(':idReceiver', $idReceiver, PDO::PARAM_INT);
			$statement->bindParam(':date', $date, PDO::PARAM_INT);
			$statement->bindParam(':montant', $montant, PDO::PARAM_INT);
			$statement->bindParam(':libelle', $libelle, PDO::PARAM_STR);
			$statement->bindParam(':valide', $valide, PDO::PARAM_INT);
			$statement->execute();
		}
		
	    public function getCompte($idSender,$idReceiver)
		{						
			$statement = $this->pdo->prepare($this->getCompte);
			$statement->bindParam(':idSender', $idSender, PDO::PARAM_INT);
			$statement->bindParam(':idReceiver', $idReceiver, PDO::PARAM_INT);
			$statement->execute();
			$result = $statement->fetchAll(PDO::FETCH_OBJ);
			return $result;
		}
		
		public function getCompteAeffect($idSender)
		{						
			$statement = $this->pdo->prepare($this->getCompteAeffect);
			$statement->bindParam(':idSender', $idSender, PDO::PARAM_INT);
			$statement->execute();
			$result = $statement->fetchAll(PDO::FETCH_OBJ);
			return $result;
		}
		
		public function hashage($mdp) {
			return hash("sha256",$mdp."UTT");
		}
	}
?>
