package fr.if26.virtualut.model;

/**
 * Created by Luwangel on 02/12/13.
 */
public class Connexion {

    //*** Attributs statiques ***//
    private static Connexion connexion;

    //*** Attributs ***//

    private boolean connecte;

    private String token;

    private Utilisateur utilisateurConnecte;

    //*** Constructeur ***//

    private Connexion() {
        this.connecte = false;
        this.token = "";
        this.utilisateurConnecte = null;
    }

    //*** Méthodes statiques ***//

    /**
     * Pattern singleton. Une seule connexion active à la fois.
     * @return
     */
    public static Connexion getInstance() {
        if(Connexion.connexion == null) {
            Connexion.connexion = new Connexion();
        }
        return  Connexion.connexion;
    }

    //*** Getters & Setters ***//

    public boolean isConnecte() {
        return connecte;
    }

    public void changeConnecte() {
        if(connecte) {
            connecte = false;
        }
        else {
            connecte = true;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }
}
