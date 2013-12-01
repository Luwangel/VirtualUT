package fr.if26.virtualut;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Luwangel on 01/12/13.
 */
public class LoginFragment extends Fragment {

    //*** Attributs ***//

    /**
     * Contient la tâche de connexion et d'identification
     */
    private ConnexionTask authentificationTask = null;

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
     * Contient l'état de la connexion
     */
    private boolean etatConnexion = false;

    /**
     * Liste des écouteurs de l'état de la connexion
     */
    private final Collection<EtatConnexionListener> etatConnexionListeners = new ArrayList<EtatConnexionListener>();

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Création de la vue
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

    //*** Méthodes et classes internes de connexion ***//

    /**
     * Méthode permettant de déclencher une tentative de connexion en fonction de ce que l'utilisateur a entré.
     */
    public void attemptLogin() {

        //Si l'on tente déjà de se connecter alors on ne fait rien.
        if (authentificationTask != null) {
            return;
        }

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
            motDePasseView.setError(getString(R.string.error_field_required));
            focusView = motDePasseView;
            cancel = true;
        } else if (motDePasse.length() < 4) {
            motDePasseView.setError(getString(R.string.error_invalid_password));
            focusView = motDePasseView;
            cancel = true;
        }

        //Vérifie que l'identifiant est valide
        if (TextUtils.isEmpty(identifiant)) {
            identifiantView.setError(getString(R.string.error_field_required));
            focusView = identifiantView;
            cancel = true;
        }

        if (cancel) {
            //Annule s'il y a une erreur et focus le champ de texte posant probème.
            focusView.requestFocus();
        } else {
            //Déclenche une ConnexionTask et affiche un spinner "connexion en cours".
            loginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            authentificationTask = new ConnexionTask();
            authentificationTask.execute((Void) null);
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

    /**
     * Classe permettant d'effectuer une tâche asynchrone de connexion à un serveur distant.
     */
    public class ConnexionTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authentificationTask = null;
            showProgress(false);

            if (success) {

            } else {
                motDePasseView.setError(getString(R.string.error_incorrect_password));
                motDePasseView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            authentificationTask = null;
            showProgress(false);
        }
    }


    //*** Gestion de l'état de la connexion et de ses écouteurs ***//

    //Gestion des écouteurs
    public void addEtatConnexionListener(EtatConnexionListener listener) {
        etatConnexionListeners.add(listener);
    }

    public void removeEtatConnexionListener(EtatConnexionListener listener) {
        etatConnexionListeners.remove(listener);
    }

    public EtatConnexionListener[] getEtatConnexionListener() {
        return etatConnexionListeners.toArray(new EtatConnexionListener[0]);
    }

    public interface EtatConnexionListener {
        public void onConnexionChange(boolean etatConnexion);
    }

    //Gestion de l'état de la connexion

    protected void etatConnexionChanged() {
        if(etatConnexion) {
            for(EtatConnexionListener listener : etatConnexionListeners) {
                listener.onConnexionChange(true);
            }
        } else {
            for(EtatConnexionListener listener : etatConnexionListeners) {
                listener.onConnexionChange(false);
            }
        }
    }
}