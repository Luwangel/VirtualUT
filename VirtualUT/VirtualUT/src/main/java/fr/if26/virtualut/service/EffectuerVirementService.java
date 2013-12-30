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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.if26.virtualut.constant.WebServiceConstants;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;
import fr.if26.virtualut.model.Transaction;

/**
 * Classe permettant d'effectuer une tâche asynchrone de connexion à un serveur distant.
 * Created by Luwangel on 30/12/13.
 */
public class EffectuerVirementService {

    //*** Attributs ***//

    private boolean success;
    private String messageErreur;

    /**
     * Liste des écouteurs sur la réussite du virement.
     */
    private final Collection<EffectuerVirementListener> effectuerVirementListeners;


    //** Constructeur **//

    /**
     * Constructeur de la classe.
     */
    public EffectuerVirementService() {
        super();
        setMessageErreur("");
        setSuccess(false);
        effectuerVirementListeners = new ArrayList<EffectuerVirementListener>();
    }


    //*** Getters & Setters ***//

    public void setMessageErreur(String messageErreur) {
        this.messageErreur = messageErreur;
    }

    public String getMessageErreur() {
        return messageErreur;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        if(success) {
            virementSuccess();
        }
        else {
            virementFail();
        }

        this.success = success;
    }

    //*** Gestion de la réussite du virement et de ses écouteurs ***//

    //Gestion des écouteurs

    /**
     * Ajoute un écouteur sur le virement.
     * @param listener
     */
    public void addConnexionListener(EffectuerVirementListener listener) {
        effectuerVirementListeners.add(listener);
    }

    /**
     * Supprime un écouteur sur le virement.
     * @param listener
     */
    public void removeConnexionListener(EffectuerVirementListener listener) {
        effectuerVirementListeners.remove(listener);
    }

    /**
     * Retourne la liste des écouteurs.
     * @return
     */
    public EffectuerVirementListener[] getConnexionListener() {
        return effectuerVirementListeners.toArray(new EffectuerVirementListener[0]);
    }

    //Gestion de l'état du virement.

    protected void virementSuccess() {
        for(EffectuerVirementListener listener : effectuerVirementListeners) {
            listener.onVirementSuccess();
        }
    }

    protected void virementFail() {
        for(EffectuerVirementListener listener : effectuerVirementListeners) {
            listener.onVirementFail();
        }
    }

    //** Classe interne **//

    public EffectuerVirementTask newEffectuerVirementTask() {
        EffectuerVirementTask effectuerVirementTask = new EffectuerVirementTask();

        return effectuerVirementTask;
    }

    public class EffectuerVirementTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            //Initialisation des attributs
            Boolean success = false;

            //Récupération des paramètres
            String idReceiver = params[0];
            String token = params[1];
            String montant = params[2];
            String libelle = params[3];
            String valide = params[4];

            //Récupération de l'adresse à laquelle envoyer la requête
            String uri = WebServiceConstants.TRANSACTION.URI;

            //Création de la requête
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.TRANSACTION.ID_RECEIVER, idReceiver));
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MEMBRE.TOKEN, token));
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.TRANSACTION.ID_RECEIVER, montant));
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.TRANSACTION.LIBELLE, libelle));
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.TRANSACTION.VALIDE, valide));

            uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");

            HttpGet httpGet = new HttpGet(uri);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

            try {

                //Effectue la requête
                HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
                String response = EntityUtils.toString(httpResponse.getEntity());

                JSONObject jsonObjectRequete = new JSONObject(response);

                if(jsonObjectRequete.getString(WebServiceConstants.ERROR).equals("false")) {
                    success = true;
                }
                else {
                    setMessageErreur(jsonObjectRequete.getString(WebServiceConstants.ERROR_MESSAGE));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setSuccess(success);
        }

        @Override
        protected void onCancelled() {
            setSuccess(false);
        }
    }
}

