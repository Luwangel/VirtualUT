package fr.if26.virtualut.constant;

/**
 * Représente l'ensemble des constantes relatives au webservice utilisé.
 */
public class WebServiceConstants
{
    public static final String ROOT = "http://5.50.248.19/virtualuts.fr/";

    public static class LOGIN
    {
        public static final String URI = ROOT + "login.php";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String TOKEN = "token";
    }

    public static class MEMBRE
    {
        public static final String URI = ROOT + "membres.php";
        public static final String TOKEN = "token";
        public static final String CONTACTS = "contacts";

        public static final String ID = "id";
        public static final String CONTACT = "contact";
        public static final String MESSAGE = "message";

        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL = "email";

        public static final String DATE = "date";
    }

    public static class VIREMENT
    {
        public static final String URI = ROOT + "membres.php";
        public static final String CONTACTS = "contacts";

        public static final String ID = "id";
        public static final String CONTACT = "contact";
        public static final String MESSAGE = "message";

        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL = "email";

        public static final String DATE = "date";
    }
}