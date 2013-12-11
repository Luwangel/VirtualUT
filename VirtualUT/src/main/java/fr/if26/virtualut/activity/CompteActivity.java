package fr.if26.virtualut.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import fr.if26.virtualut.fragment.MenuCompteFragment;
import fr.if26.virtualut.fragment.MenuMainFragment;
import fr.if26.virtualut.R;

public class CompteActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        // Set active main menu tab
        MenuMainFragment mainMenu = (MenuMainFragment) getSupportFragmentManager().findFragmentById(R.id.main_menu_fragment);
        mainMenu.setActive(MenuMainFragment.COMPTE);
        mainMenu.setListeners(MenuMainFragment.COMPTE);

        //Set active submenu
        MenuCompteFragment menuCompteController = (MenuCompteFragment) getSupportFragmentManager().findFragmentById(R.id.menu_compte_fragment);
        menuCompteController.setActive(MenuCompteFragment.COMPTE);
    }



}
