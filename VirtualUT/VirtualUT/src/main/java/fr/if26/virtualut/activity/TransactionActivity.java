package fr.if26.virtualut.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import fr.if26.virtualut.R;
import fr.if26.virtualut.fragment.MenuMainFragment;
import fr.if26.virtualut.fragment.MenuTransactionFragment;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class TransactionActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        // Set active main menu tab
        MenuMainFragment mainMenu = (MenuMainFragment) getSupportFragmentManager().findFragmentById(R.id.main_menu_fragment);
        mainMenu.setActive(MenuMainFragment.TRANSACTION);
        mainMenu.setListeners(MenuMainFragment.TRANSACTION);

        //Set active submenu
        MenuTransactionFragment menuTransactionController = (MenuTransactionFragment) getSupportFragmentManager().findFragmentById(R.id.menu_transaction_fragment);
        menuTransactionController.setActive(MenuTransactionFragment.TRANSACTION);


    }
}
