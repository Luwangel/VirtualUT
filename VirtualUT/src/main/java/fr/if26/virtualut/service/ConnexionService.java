package fr.if26.virtualut.service;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe permettant d'effectuer une tâche asynchrone de connexion à un serveur distant.
 * Created by Luwangel on 03/12/13.
 */
public class ConnexionService extends AsyncTask<String, Void, Boolean> {

    //*** Attributs ***//

    /**
     * Liste des écouteurs de l'état de la connexion
     */
    private final Collection<ConnexionSuccessListener> connexionSuccessListeners = new ArrayList<ConnexionSuccessListener>();

    /**
     * Réussite de la connexion ou non.
     */
    private Boolean success;

    /**
     * Contient le token après que la connexion ait réussi. Vide si la connexion ne réussit pas.
     */
    private String token;

    //** Constructeur **//

    /**
     * Constructeur de la classe.
     */
    public ConnexionService() {
        super();

        this.success = false;
        this.token = "";
    }

    //** Getters & Setters **//

    /**
     * Retourne le token.
     * @return
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Retourne vrai si la connexion a réussi, faux sinon.
     * @return
     */
    public Boolean isSuccess() {
        return this.success;
    }

    /**
     * Passe le booléen success à vrai.
     */
    private void setSuccess() {
        this.success = true;
        this.connexionSuccess(); //Déclenche l'événement
    }

    /**
     * Passe le booléen success à faux.
     */
    private void setFail() {
        this.success = false;
        this.connexionFail(); //Déclenche l'événement
    }

    //** Méthodes **//

    @Override
    protected Boolean doInBackground(String... params) {

        String token = "";

        boolean success = false;

        String login = params[0];
        String password = params[1];

        try {
            this.connexionTry();

            //Network access.
            success = true;
            //Simule le temps de connexion
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            success = false;
        }

        this.token = token;
        return success;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if(success) {
            this.setSuccess();
        }
        else {
            this.setFail();
        }
    }

    @Override
    protected void onCancelled() {
        this.setFail();
        this.token = "";
    }

    //*** Gestion de la réussite de la connexion et de ses écouteurs ***//

    //Gestion des écouteurs

    /**
     * Ajoute un écouteur sur la connexion.
     * @param listener
     */
    public void addConnexionListener(ConnexionSuccessListener listener) {
        connexionSuccessListeners.add(listener);
    }

    /**
     * Supprime un écouteur sur la connexion.
     * @param listener
     */
    public void removeConnexionListener(ConnexionSuccessListener listener) {
        connexionSuccessListeners.remove(listener);
    }

    /**
     * Retourne la liste des écouteurs.
     * @return
     */
    public ConnexionSuccessListener[] getConnexionListener() {
        return connexionSuccessListeners.toArray(new ConnexionSuccessListener[0]);
    }

    /**
     * Interface pour implémenter un écouteur sur la connexion.
     */
    public interface ConnexionSuccessListener {
        public void onConnexionSuccess();
        public void onConnexionFail();
        public void onConnexionTry();
    }

    //Gestion de l'état de la connexion

    protected void connexionSuccess() {
        for(ConnexionSuccessListener listener : connexionSuccessListeners) {
            listener.onConnexionSuccess();
        }
    }

    protected void connexionFail() {
        for(ConnexionSuccessListener listener : connexionSuccessListeners) {
            listener.onConnexionFail();
        }
    }

    protected void connexionTry() {
        for(ConnexionSuccessListener listener : connexionSuccessListeners) {
            listener.onConnexionTry();
        }
    }
}
