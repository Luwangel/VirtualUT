package fr.if26.virtualut.view;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fr.if26.virtualut.R;

public class WelcomeActivity extends ActionBarActivity implements LoginFragment.EtatConnexionListener {

    //*** Attributs ***//

    //Fragment de connexion
    private LoginFragment loginFragment = null;

    //*** Implémentation des méthodes d'une activity ***//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupFragments() {
        if (loginFragment == null) {
            loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_login);
            loginFragment.addEtatConnexionListener(this);
        }
    }

    //*** Implémentation des méthodes ***//

    /**
     * Si la connexion est effective, on lance la classe MonCompteActivity
     */
    @Override
    public void onConnexionChange() {
        if(loginFragment.isConnected()) {
            Log.d("Debug","Lancement de l'activity MonCompteActivity.");
            startActivity(new Intent(this, MonCompteActivity.class));
        }
    }
}
