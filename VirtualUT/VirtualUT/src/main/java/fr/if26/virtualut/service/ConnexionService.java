package fr.if26.virtualut.service;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.if26.virtualut.constant.WebServiceConstants;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;

/**
 * Classe permettant d'effectuer une tâche asynchrone de connexion à un serveur distant.
 * Created by Luwangel on 03/12/13.
 */

public class ConnexionService {
    //*** Attributs ***//

    /**
     * Liste des écouteurs de l'état de la connexion
     */
    private final Collection<ConnexionSuccessListener> connexionSuccessListeners = new ArrayList<ConnexionSuccessListener>();

    //** Constructeur **//

    /**
     * Constructeur de la classe.
     */
    public ConnexionService() {
        super();

        if(Connexion.getInstance().isConnecte()) {
            Connexion.getInstance().deconnexion();
        }
    }

    //** Getters & Setters **//

    /**
     * Passe le booléen success à vrai.
     */
    public void setSuccess() {
        this.connexionSuccess(); //Déclenche l'événement
    }

    /**
     * Passe le booléen success à faux.
     */
    public void setFail() {
        this.connexionFail(); //Déclenche l'événement
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

    public ConnexionTask newConnexionTask() {
        ConnexionTask connexionTask = new ConnexionTask();

        return connexionTask;
    }

    //** Classe interne **//

    public class ConnexionTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            //Initialisation des attributs
            Boolean success = false;

            //Récupération des paramètres
            String login = params[0];
            String password = params[1];

            //Récupération de l'adresse à laquelle envoyer la requête
            String uri = WebServiceConstants.LOGIN.URI;

            //Initialisation des variables temporaires
            String token = "";

            //Création de la requête
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.LOGIN.LOGIN, login));
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.LOGIN.PASSWORD, password));

            uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");

            HttpGet httpGet = new HttpGet(uri);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

            try {
                //Déclenchement de l'événement
                connexionTry();

                //Effectue la requête
                HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
                String response = EntityUtils.toString(httpResponse.getEntity());

                JSONObject jsonObjectRequete = new JSONObject(response);

                jsonObjectRequete.getString(WebServiceConstants.MEMBRE.TOKEN);

                if(jsonObjectRequete.getString(WebServiceConstants.ERROR).equals("false")) {

                    JSONObject jsonObjectMembre = jsonObjectRequete.getJSONObject(WebServiceConstants.MEMBRE.MEMBRE);

                    Membre membre = new Membre(
                        Integer.parseInt(jsonObjectMembre.getString(WebServiceConstants.MEMBRE.ID)),
                        jsonObjectMembre.getString(WebServiceConstants.MEMBRE.NOM),
                        jsonObjectMembre.getString(WebServiceConstants.MEMBRE.PRENOM),
                        jsonObjectMembre.getString(WebServiceConstants.MEMBRE.EMAIL),
                        Double.parseDouble(jsonObjectMembre.getString(WebServiceConstants.MEMBRE.CREDIT))
                    );

                    Connexion connexion = Connexion.getInstance();
                    connexion.connexion(jsonObjectRequete.getString(WebServiceConstants.MEMBRE.TOKEN),membre);

                    success = true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            token = token;
            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success) {
                setSuccess();
            }
            else {
                setFail();
            }
        }

        @Override
        protected void onCancelled() {
            setFail();
        }
    }
}
