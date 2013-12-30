package fr.if26.virtualut.constant;

/**
 * Représente l'ensemble des constantes relatives au webservice utilisé.
 */
public class WebServiceConstants
{
    public static final String ROOT = "http://breci.smoi.in/VirtualUt/";
    public static final String ERROR = "error";
    public static final String ERROR_MESSAGE = "message";

    public static final String TRUE = "1";
    public static final String FALSE = "0";

    public static class LOGIN
    {
        public static final String URI = ROOT + "login.php";

        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
    }

    public static class MEMBRE
    {
        public static final String MEMBRE = "membre";

        public static final String ID = "idMembre";
        public static final String NOM = "nom";
        public static final String PRENOM = "prenom";
        public static final String EMAIL = "email";
        public static final String CREDIT = "credit";

        public static final String TOKEN = "token";
    }

    public static class TRANSACTION
    {
        public static final String URI = ROOT + "transfertByMembre.php";

        public static final String COMPTES = "comptes";

        public static final String ID = "idCompte";
        public static final String SENDER = "emetteur";
        public static final String ID_RECEIVER = "idReceiver";
        public static final String RECEIVER = "recepteur";
        public static final String DATE = "date";
        public static final String MONTANT = "montant";
        public static final String LIBELLE = "libelle";
        public static final String VALIDE = "valide";

    }
}