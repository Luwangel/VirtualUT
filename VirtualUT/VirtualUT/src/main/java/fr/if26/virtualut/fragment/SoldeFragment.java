package fr.if26.virtualut.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;

/**
 * Created by Luwangel on 31/12/13.
 */
public class SoldeFragment extends Fragment {

    //*** Attributs ***//

    private TextView textViewNom;
    private TextView textViewSolde;
    private TextView textViewCredit;

    private String nomComplet;
    private String credit;

    private Membre membreConnecte;

    private Time time;

    //*** Constructeur ***//

    public SoldeFragment() {
        super();
        initialiserFragment();
    }

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initialiserFragment();
        return inflater.inflate(R.layout.fragment_solde, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiserFragment();

        //Récupère les vues
        textViewNom = (TextView) getActivity().findViewById(R.id.textViewNom);
        textViewCredit = (TextView) getActivity().findViewById(R.id.textViewCredit);

        //Chargement de la date du jour
        textViewSolde = (TextView) view.findViewById(R.id.textViewSolde);
        textViewSolde.setText("Solde au "+time.format("%d/%m/%Y")+": ");

        //Pré rempli les champs
        textViewNom.setText(nomComplet);
        textViewCredit.setText(credit);
    }

    //*** Getters & Setters ***//


    //*** Méthodes ***//

    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {

            membreConnecte = Connexion.getInstance().getMembreConnecte();
            nomComplet = membreConnecte.getPrenom() + " " + membreConnecte.getNom();
            credit = membreConnecte.getCredit() + " crédits";

            //Chargement de la date du jour
            time = new Time(Time.getCurrentTimezone());
            time.setToNow();
        }
    }
}