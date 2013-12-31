package fr.if26.virtualut.fragment;

/**
 * Created by Thanh-Tuan on 14/12/13.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;


public class TabCompteFragment extends Fragment {

    //*** Attributs ***//

    //Fragments
    ListFragment listFragment;
    SoldeFragment soldeFragment;

    //Vues
    private Button buttonTriDate;
    private Button buttonTriMontant;

    //*** Constructeur ***//

    public TabCompteFragment() {
        super();
        initialiserFragment();
    }


    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_compte, container, false);

        Membre membreConnecte = Connexion.getInstance().getMembreConnecte();

        //Fragments indépendants
        listFragment = new ListFragment(membreConnecte.getTransactions());
        soldeFragment = new SoldeFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.listFragment, listFragment);
        transaction.add(R.id.soldeFragment, soldeFragment);
        transaction.commit();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Récupère les vues
        buttonTriDate = (Button) getActivity().findViewById(R.id.buttonTriDate);
        buttonTriMontant = (Button) getActivity().findViewById(R.id.buttonTriMontant);
    }

    //*** Méthodes ***//

    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {

        }
    }
}
