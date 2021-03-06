package fr.if26.virtualut.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.if26.virtualut.R;
import fr.if26.virtualut.fragment.MenuMainFragment;
import fr.if26.virtualut.fragment.PagerFragment;
import fr.if26.virtualut.fragment.TabPlusTardFragment;
import fr.if26.virtualut.fragment.TabEffectuerTransactionFragment;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.ListeMembres;
import fr.if26.virtualut.model.Transaction;
import fr.if26.virtualut.service.RecupererMembresService;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class TransactionActivity extends FragmentActivity {

    //*** Attributs ***//

    private PagerAdapter mPagerAdapter;
    private ViewPager pager;
    private List<Fragment> fragments;
    private RecupererMembresService recupererMembresService;

    //*** Implémentation des méthodes d'une activity ***//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Rotation de l'écran
        if(savedInstanceState != null && !savedInstanceState.get("rotationecran").equals("true")) {
            Connexion.getInstance().deconnexion();
        }

        setContentView(R.layout.activity_main);

        //Récupère la liste des membres (pour autocomplete)
        attemptRecupererListeMembres(Connexion.getInstance().getToken());

        // Set active main menu tab
        MenuMainFragment mainMenu = (MenuMainFragment) getSupportFragmentManager().findFragmentById(R.id.main_menu_fragment);
        mainMenu.setActive(MenuMainFragment.TRANSACTION);
        mainMenu.setListeners(MenuMainFragment.TRANSACTION);

        fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this,TabEffectuerTransactionFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,TabPlusTardFragment.class.getName()));

        this.mPagerAdapter = new PagerFragment(super.getSupportFragmentManager(), fragments);

        pager = (ViewPager) super.findViewById(R.id.viewpager);
        // Affectation de l'adapter au ViewPager
        pager.setAdapter(this.mPagerAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!Connexion.getInstance().isConnecte()) {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putString("rotationecran", "true");
    }

    //*** Getters & Setters ***//

    public ViewPager getPager(){
        return this.pager;
    }

    //*** Changement d'onglet ***//

    public void switchToEffectuer(View view) {
        ViewPager viewPager = getPager();
        viewPager.setCurrentItem(1);
    }

    public void switchToNouveau(View view) {
        ViewPager viewPager = getPager();
        viewPager.setCurrentItem(0);
    }

    public List<Fragment> getFragments() { return this.fragments;}


    //*** Méthodes ***//

    public boolean attemptRecupererListeMembres(String token) {

        if(!ListeMembres.getInstance().getListeMembres().isEmpty()) {
            return false;
        }

        //Valeur de retour
        Boolean success = false;

        //Vérifie la connexion à internet
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Effectue la requête
        if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {

            recupererMembresService = new RecupererMembresService();
            RecupererMembresService.RecupererMembresTask recupererMembresTask = recupererMembresService.newRecupererMembresTask();
            recupererMembresTask.execute(token);

            try {
                success = recupererMembresTask.get(20000, TimeUnit.MILLISECONDS);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        else { //Pas de connexion
            Toast.makeText(this, R.string.login_error_noconnection, Toast.LENGTH_LONG).show();
        }

        return success;
    }

    //*** Menu ***//

    /**
     * Création du menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    /**
     * Appellée lors du clic sur l'une des options du menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_infos:

                //Deconnection
                Connexion.getInstance().deconnexion();
                Toast.makeText(this,R.string.menu_deconnexion_toast, Toast.LENGTH_SHORT);

                //Ouvre l'activity Welcome
                startActivity(new Intent(TransactionActivity.this, WelcomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
