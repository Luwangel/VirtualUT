package fr.if26.virtualut.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import fr.if26.virtualut.R;
import fr.if26.virtualut.activity.CompteActivity;
import fr.if26.virtualut.activity.RechercheActivity;
import fr.if26.virtualut.activity.TransactionActivity;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class MenuMainFragment extends Fragment implements OnClickListener {


    public static final int COMPTE = 0;
    public static final int TRANSACTION = 1;
    public static final int RECHERCHE = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_menu_bar, container, false);

        return view;
    }



    public void setActive(int tab)
    {
        this.getView().findViewById(R.id.compte).setBackgroundResource(R.drawable.main_menu_button);
        this.getView().findViewById(R.id.transaction).setBackgroundResource(R.drawable.main_menu_button);
        this.getView().findViewById(R.id.recherche).setBackgroundResource(R.drawable.main_menu_button);

        switch(tab)
        {
            case COMPTE:
                this.getView().findViewById(R.id.compte).setBackgroundResource(R.drawable.main_menu_button_active);
                break;
            case TRANSACTION:
                this.getView().findViewById(R.id.transaction).setBackgroundResource(R.drawable.main_menu_button_active);
                break;
            case RECHERCHE:
                this.getView().findViewById(R.id.recherche).setBackgroundResource(R.drawable.main_menu_button_active);
                break;
        }
    }

    public void setListeners(int tab)
    {
        switch(tab)
        {
            case COMPTE:
                this.getView().findViewById(R.id.transaction).setOnClickListener(this);
                this.getView().findViewById(R.id.recherche).setOnClickListener(this);
                break;
            case TRANSACTION:
                this.getView().findViewById(R.id.compte).setOnClickListener(this);
                this.getView().findViewById(R.id.recherche).setOnClickListener(this);
                break;
            case RECHERCHE:
                this.getView().findViewById(R.id.compte).setOnClickListener(this);
                this.getView().findViewById(R.id.transaction).setOnClickListener(this);
                break;
        }
    }


    @Override
    public void onClick(View view) {

        Intent intent = null;

        switch(view.getId())
        {
            case R.id.compte:
                intent = new Intent(this.getActivity(), CompteActivity.class);
                break;
            case R.id.transaction:
                intent = new Intent(this.getActivity(), TransactionActivity.class);
                break;
            case R.id.recherche:
                intent = new Intent(this.getActivity(), RechercheActivity.class);
                break;
        }
        if(intent != null)
        {
            this.startActivity(intent);
            // No animation
            this.getActivity().overridePendingTransition(0, 0);
            this.getActivity().finish();
        }
    }
}
