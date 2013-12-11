package fr.if26.virtualut.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fr.if26.virtualut.fragment.ListFragment;
import fr.if26.virtualut.fragment.NouveauVirementFragment;
import fr.if26.virtualut.fragment.ViewPagerAdapterFragment;
import fr.if26.virtualut.R;

/**
 * Activité principale de l'application ouverte au démarrage.
 * Contient l'ensemble des fragments de gestion des listes de tweets et d'ajout d'un nouveau tweet.
 */
public class MonCompteActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ViewPager viewPager;

    /**
     * Getter de ViewPager
     * @return
     */
    public ViewPager getViewPager() {
        return viewPager;
    }

    /**

     * Adapter contenant les fragments à afficher dans le ViewPager
     */
    private ViewPagerAdapterFragment viewPagerAdapter;

    /**
     * Getter de ViewPagerAdapter
     * @return
     */
    public ViewPagerAdapterFragment getViewPagerAdapter() {
        return viewPagerAdapter;
    }

    /**
     * Création de l'activité, affiche la vue et initialise le ViewPager
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moncompte);

        this.setupViewPager();
    }


/* Options du menu */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    /**
     * Appellée lors du clic sur l'une des options du menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

/* Méthodes privées d'initialisation */

    /**
     *  Création des fragments et du ViewPagerAdapter
     */
    private void setupViewPager() {

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up adapter
        ArrayList<Fragment> fragments = new ArrayList<Fragment>() ;
        fragments.add(ListFragment.newInstance("Mon compte"));
        fragments.add(NouveauVirementFragment.newInstance("Nouveau virement"));

        viewPagerAdapter = new ViewPagerAdapterFragment(getSupportFragmentManager(),fragments);

        // Set up the ViewPager with the sections adapter.
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.viewPager.setAdapter(viewPagerAdapter);

        // Set up the gesture to swipe between tab.
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        ArrayList<String> titles = new ArrayList<String>();

        titles.add(((ListFragment) viewPagerAdapter.getItem(0)).getTitle());
        titles.add(((NouveauVirementFragment) viewPagerAdapter.getItem(1)).getTitle());


        for(String s : titles) {
            actionBar.addTab(actionBar.newTab().setText(s).setTabListener(this));
        }
    }

/* Actions sur la tab sélectionnée */

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
