package fr.if26.virtualut.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.service.ConnexionService;

/**
 * Created by Luwangel on 02/12/13.
 */
public class LoginFragment extends Fragment implements ConnexionService.ConnexionSuccessListener {

    //*** Attributs ***//

    public static final String TAG = "LoginFragment";

    //Contenu des champs de texte
    private String identifiant;
    private String motDePasse;

    //Composants graphiques
    private EditText identifiantView;
    private EditText motDePasseView;
    private View loginFormView;
    private View loginStatusView;
    private TextView loginStatusMessageView;

    //Connexion

    /**
     * Service de connexion utilisé par la classe pour se connecter.
     */
    private ConnexionService connexionService;

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Récupération des composants graphiques
        identifiantView = (EditText) getView().findViewById(R.id.identifiant_editText);
        identifiantView.setText(identifiant);

        motDePasseView = (EditText) getView().findViewById(R.id.motdepasse_editText);
        motDePasseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        loginFormView = getView().findViewById(R.id.login_form);
        loginStatusView = getView().findViewById(R.id.login_status);
        loginStatusMessageView = (TextView) getView().findViewById(R.id.login_status_message);

        getView().findViewById(R.id.connexion_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /*** Getters & Setters ***/

    public ConnexionService getConnexionService() {
        return connexionService;
    }

    public void setConnexionService(ConnexionService connexionService) {
        this.connexionService = connexionService;
        this.connexionService.addConnexionListener(this); //Inscrit la classe en tant qu'écouteur
    }

    //*** Méthodes et classes internes de connexion ***//

    /**
     * Méthode permettant de déclencher une tentative de connexion en fonction de ce que l'utilisateur a entré.
     */
    public void attemptLogin() {

        if(connexionService == null) {
            //S'il n'y a pas de service de connexion on ne peut pas se connecter.
            return;
        }
        else if(this.connexionService.isSuccess()) {
            //Si la connexion est déjà etablie on ne se reconnecte pas.
            return;
        }

        //Dans tous les autres cas

        //Réinitialise les erreurs.
        identifiantView.setError(null);
        motDePasseView.setError(null);

        //Stocke les données des champs de texte.
        identifiant = identifiantView.getText().toString();
        motDePasse = motDePasseView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Vérifie que le mot de passe est valide.
        if (TextUtils.isEmpty(motDePasse)) {
            motDePasseView.setError(getString(R.string.login_error_field_required));
            focusView = motDePasseView;
            cancel = true;
        } else if (motDePasse.length() < 4) {
            motDePasseView.setError(getString(R.string.login_error_invalid_password));
            focusView = motDePasseView;
            cancel = true;
        }

        //Vérifie que l'identifiant est valide
        if (TextUtils.isEmpty(identifiant)) {
            identifiantView.setError(getString(R.string.login_error_field_required));
            focusView = identifiantView;
            cancel = true;
        }

        if (cancel) {
            //Annule s'il y a une erreur et focus le champ de texte posant probème.
            focusView.requestFocus();
        } else {
            //Déclenche un ConnexionService et affiche un spinner "connexion en cours".
            loginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);

            //Vérifie la connexion à internet
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) { //Connexion
                connexionService.execute(this.identifiant,this.motDePasse);
            }
            else { //Pas de connexion
                Toast.makeText(this.getActivity(),R.string.login_error_noconnection,Toast.LENGTH_LONG);
                showProgress(false);
            }
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginStatusView.setVisibility(View.VISIBLE);
            loginStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            loginFormView.setVisibility(View.VISIBLE);
            loginFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    //*** Implémentation de l'interface ConnexionService.ConnexionSuccessListener ***//
    @Override
    public void onConnexionTry() {
        showProgress(true);
    }

    @Override
    public void onConnexionSuccess() {
        showProgress(false);
        if(connexionService.isSuccess())
            Connexion.getInstance().setToken(connexionService.getToken());
    }

    @Override
    public void onConnexionFail() {
        showProgress(false);

        motDePasseView.setError(getString(R.string.login_error_incorrect_password));
        motDePasseView.requestFocus();
    }
}