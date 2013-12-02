package fr.if26.virtualut.view;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fr.if26.virtualut.R;

public class WelcomeActivity extends ActionBarActivity implements LoginFragment.EtatConnexionListener {

    //*** Attributs ***//

    //Fragment de connexion
    private LoginFragment loginFragment;

    //*** Implémentation des méthodes d'une activity ***//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //setupFragments();
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

        final FragmentManager fm = getSupportFragmentManager();

        this.loginFragment = (LoginFragment) fm.findFragmentByTag(LoginFragment.TAG);

        if (this.loginFragment == null) {
            loginFragment = new LoginFragment();

            loginFragment.addEtatConnexionListener(this);
            this.onConnexionChange(true);
        }
    }

    //*** Implémentation des méthodes ***//

    /**
     * Si la connexion est effective, on lance la classe MonCompteActivity
     * @param etatConnexion
     */
    @Override
    public void onConnexionChange(boolean etatConnexion) {
        if(etatConnexion) {
            startActivity(new Intent(this, MonCompteActivity.class));
        }
    }
}
