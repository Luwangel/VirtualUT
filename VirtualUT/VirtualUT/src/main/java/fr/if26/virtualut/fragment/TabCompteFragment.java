package fr.if26.virtualut.fragment;

/**
 * Created by Thanh-Tuan on 14/12/13.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.if26.virtualut.R;


public class TabCompteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_compte, container, false);

        //Chargement de la date du jour
        TextView tv = (TextView) view.findViewById(R.id.solde);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        tv.setText("Solde au "+today.format("%d/%m/%Y")+": ");


        // Fragment emboité dans un fragment (Nested Fragment) pour la liste des transactions
        android.support.v4.app.ListFragment listFragment = new fr.if26.virtualut.fragment.ListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child_fragment, listFragment).commit();
        return view;
    }
}
