package fr.if26.virtualut;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import fr.if26.virtualut.compte.ViewPagerAdapter;

/**
 * Created by Luwangel on 01/12/13.
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
    private ViewPagerAdapter viewPagerAdapter;

    /**
     * Getter de ViewPagerAdapter
     * @return
     */
    public ViewPagerAdapter getViewPagerAdapter() {
        return viewPagerAdapter;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
    }

    /**
     *  Création des fragments et du ViewPagerAdapter
     */
    private void setupViewPager() {

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up adapter
        ArrayList<Fragment> fragments = new ArrayList<Fragment>() ;

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);

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

        titles.add("Mon compte");
        titles.add("Virements");

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