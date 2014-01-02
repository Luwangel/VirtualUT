package fr.if26.virtualut.service;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.List;

import fr.if26.virtualut.constant.WebServiceConstants;
import fr.if26.virtualut.model.Membre;
import fr.if26.virtualut.model.ListeMembres;

/**
 * Classe permettant d'effectuer une tâche asynchrone d'accès à un serveur distant.
 * Created by Luwangel on 02/01/14.
 */
public class RecupererMembresService {

    //*** Attributs ***//

    private boolean success;
    private String messageErreur;


    //** Constructeur **//

    /**
     * Constructeur de la classe.
     */
    public RecupererMembresService() {
        super();
        setMessageErreur("");
        setSuccess(false);
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
        this.success = success;
    }

    //** Classe interne **//

    public RecupererMembresTask newRecupererMembresTask() {
        RecupererMembresTask effectuerVirementTask = new RecupererMembresTask();

        return effectuerVirementTask;
    }

    public class RecupererMembresTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            //Initialisation des attributs
            Boolean success = false;

            //Récupération des paramètres
            String token = params[0];

            //Récupération de l'adresse à laquelle envoyer la requête
            String uri = WebServiceConstants.MEMBRES.URI;

            //Création de la requête
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MEMBRE.TOKEN, token));

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
                    //Récupère la liste des membres
                    ArrayList<Membre> membresList = new ArrayList<Membre>();

                    JSONArray jsonArrayMembres = jsonObjectRequete.getJSONArray(WebServiceConstants.MEMBRES.LISTE);

                    //Boucle sur chaque transaction
                    for(int i=0;i<jsonArrayMembres.length();i++) {

                        JSONObject jsonObjectMembre = jsonArrayMembres.getJSONObject(i);

                        int id = Integer.valueOf(jsonObjectMembre.getString(WebServiceConstants.MEMBRE.ID));
                        String nom = jsonObjectMembre.getString(WebServiceConstants.MEMBRE.NOM);
                        String prenom = jsonObjectMembre.getString(WebServiceConstants.MEMBRE.PRENOM);

                        membresList.add(new Membre(id,nom,prenom));

                    }

                    ListeMembres.getInstance().setListeMembres(membresList);
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
