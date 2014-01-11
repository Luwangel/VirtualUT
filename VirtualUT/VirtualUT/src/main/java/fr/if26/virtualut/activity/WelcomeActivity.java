package fr.if26.virtualut.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.if26.virtualut.R;
import fr.if26.virtualut.fragment.LoginFragment;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.service.ConnexionService;
import fr.if26.virtualut.service.ConnexionSuccessListener;

public class WelcomeActivity extends ActionBarActivity implements ConnexionSuccessListener {

    //*** Attributs ***//

    //Fragment de connexion
    private LoginFragment loginFragment = null;

    private ConnexionService connexionService;

    //*** Implémentation des méthodes d'une activity ***//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.connexionService = new ConnexionService();
        this.connexionService.addConnexionListener(this);
        setupFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Connexion.getInstance().isConnecte()) {
            Connexion.getInstance().deconnexion();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    //*** Méthodes spécifiques à cette activity ***//

    private boolean lancerMonCompteActivity() {
        if(Connexion.getInstance().isConnecte()) {
            startActivity(new Intent(this, CompteActivity.class));
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Permet de récupérer le fragment créé à partir de la vue.
     */
    private void setupFragments() {
        if (loginFragment == null) {
            loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_login);
        }
        loginFragment.setConnexionService(this.connexionService);
    }

    //*** Implémentation des méthodes ***//

    /**
     * Si la connexion est réussie, on lance la classe CompteActivity
     */
    @Override
    public void onConnexionSuccess() {
        if(Connexion.getInstance().isConnecte()) {
            Toast.makeText(this,R.string.login_success,Toast.LENGTH_LONG).show();
            this.lancerMonCompteActivity();
        }
    }

    @Override
    public void onConnexionFail() {
        Toast.makeText(this,R.string.login_error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnexionTry() {
    }
}
