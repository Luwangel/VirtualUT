package fr.if26.virtualut.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import fr.if26.virtualut.R;
import fr.if26.virtualut.fragment.MenuMainFragment;
import fr.if26.virtualut.model.Connexion;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class AideActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && !savedInstanceState.get("rotationecran").equals("true")) {
            Connexion.getInstance().deconnexion();
        }

        setContentView(R.layout.activity_aide);

        // Set active main menu tab
        MenuMainFragment mainMenu = (MenuMainFragment) getSupportFragmentManager().findFragmentById(R.id.main_menu_fragment);
        mainMenu.setActive(MenuMainFragment.RECHERCHE);
        mainMenu.setListeners(MenuMainFragment.RECHERCHE);
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
}
