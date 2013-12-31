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
import android.widget.Button;
import android.widget.TextView;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;


public class TabCompteFragment extends Fragment {

    //*** Attributs ***//

    private TextView textViewNom;
    private TextView textViewSolde;
    private TextView textViewCredit;

    private Button buttonTriDate;
    private Button buttonTriMontant;

    private String nomComplet;
    private String credit;


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

        // Fragment emboité dans un fragment (Nested Fragment) pour la liste des transactions
        android.support.v4.app.ListFragment listFragment = new fr.if26.virtualut.fragment.ListFragment(membreConnecte.getTransactions());

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child_fragment, listFragment).commit();
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Récupère les vues
        textViewNom = (TextView) getActivity().findViewById(R.id.textViewNom);
        textViewCredit = (TextView) getActivity().findViewById(R.id.textViewCredit);

        buttonTriDate = (Button) getActivity().findViewById(R.id.buttonTriDate);
        buttonTriMontant = (Button) getActivity().findViewById(R.id.buttonTriMontant);

        //Chargement de la date du jour
        textViewSolde = (TextView) view.findViewById(R.id.textViewSolde);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        textViewSolde.setText("Solde au "+today.format("%d/%m/%Y")+": ");

        //Pré rempli les champs
        textViewNom.setText(this.nomComplet);
        textViewCredit.setText(this.credit);
    }

    //*** Méthodes ***//

    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {
            Membre membreConnecte = Connexion.getInstance().getMembreConnecte();

            this.nomComplet = membreConnecte.getPrenom() + " " + membreConnecte.getNom();
            this.credit = membreConnecte.getCredit() + " crédits";
        }
    }
}
