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

    private Membre membreConnecte;

    //*** Constructeur ***//

    private Connexion() {
        this.connecte = false;
        this.token = "";
        this.membreConnecte = null;
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

    public Membre getMembreConnecte() {
        return membreConnecte;
    }

    public void setMembreConnecte(Membre membreConnecte) {
        this.membreConnecte = membreConnecte;
    }
}
