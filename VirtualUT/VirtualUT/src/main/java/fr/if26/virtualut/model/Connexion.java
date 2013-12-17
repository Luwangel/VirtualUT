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
        initialiser();
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

    /**
     * Initialise la connexion (par défaut déconnectée).
     */
    private void initialiser() {
        this.connecte = false;
        this.token = "";
        this.membreConnecte = null;
    }

    public boolean isConnecte() {
        return connecte;
    }

    public void deconnexion() {
        initialiser();
    }

    public void connexion(String token, Membre membreConnecte) {
        this.setToken(token);
        this.setMembreConnecte(membreConnecte);
        this.connecte = true;
    }

    public String getToken() {
        return token;
    }

    private void setToken(String token) {
        this.token = token;
    }

    public Membre getMembreConnecte() {
        return membreConnecte;
    }

    private void setMembreConnecte(Membre membreConnecte) {
        this.membreConnecte = membreConnecte;
    }
}
