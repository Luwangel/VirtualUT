package fr.if26.virtualut.modele;

/**
 * Created by Luwangel on 02/12/13.
 */
public class Connexion {

    //*** Attributs statiques ***//


    //*** Attributs ***//

    public boolean connecte;

    private String token;

    private Utilisateur utilisateurConnecte;

    //*** Constructeur ***//


    public Connexion() {
        this.connecte = false;
        this.token = "";
        this.utilisateurConnecte = null;
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
