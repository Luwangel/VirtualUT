package fr.if26.virtualut.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import fr.if26.virtualut.R;
import fr.if26.virtualut.fragment.MenuMainFragment;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class RechercheActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        // Set active main menu tab
        MenuMainFragment mainMenu = (MenuMainFragment) getSupportFragmentManager().findFragmentById(R.id.main_menu_fragment);
        mainMenu.setActive(MenuMainFragment.RECHERCHE);
        mainMenu.setListeners(MenuMainFragment.RECHERCHE);
    }
}
