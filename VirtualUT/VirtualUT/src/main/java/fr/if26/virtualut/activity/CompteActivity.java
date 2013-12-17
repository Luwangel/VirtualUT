package fr.if26.virtualut.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Vector;

import fr.if26.virtualut.fragment.MenuMainFragment;
import fr.if26.virtualut.R;
import fr.if26.virtualut.fragment.MyPagerFragment;
import fr.if26.virtualut.fragment.TabCompteFragment;
import fr.if26.virtualut.fragment.TabProfilFragment;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;

public class CompteActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && !savedInstanceState.get("rotationecran").equals("true")) {
            Connexion.getInstance().deconnexion();
        }

        setContentView(R.layout.activity_main);

        // Set active main menu tab
        MenuMainFragment mainMenu = (MenuMainFragment) getSupportFragmentManager().findFragmentById(R.id.main_menu_fragment);
        mainMenu.setActive(MenuMainFragment.COMPTE);
        mainMenu.setListeners(MenuMainFragment.COMPTE);

        //Création de la liste des fragments
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this,TabCompteFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,TabProfilFragment.class.getName()));

        //Création de l'adapter
        this.mPagerAdapter = new MyPagerFragment(super.getSupportFragmentManager(), fragments);

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

    public ViewPager getPager(){return this.pager;}


    public void switch_to_compte(View view) {
        ViewPager viewPager = getPager();
        viewPager.setCurrentItem(0);
    }

    public void switch_to_profil(View view) {
        ViewPager viewPager = getPager();
        viewPager.setCurrentItem(1);
    }
}
