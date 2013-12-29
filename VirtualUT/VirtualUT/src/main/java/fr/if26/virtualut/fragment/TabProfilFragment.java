package fr.if26.virtualut.fragment;

/**
 * Created by Thanh-Tuan on 14/12/13.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;

public class TabProfilFragment extends Fragment {

    //*** Attributs ***//

    private View layoutProfil;
    private TextView fieldContentNom;
    private TextView fieldContentMail;
    private TextView fieldContentCredit;

    private String nomComplet;
    private String mail;
    private String credit;

    //*** Constructeur ***//

    public TabProfilFragment() {
        super();
        initialiserFragment();
    }

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_profil, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutProfil = getActivity().findViewById(R.id.layoutProfil);
        fieldContentNom = (TextView) getActivity().findViewById(R.id.fieldContentNom);
        fieldContentMail = (TextView) getActivity().findViewById(R.id.fieldContentMail);
        fieldContentCredit = (TextView) getActivity().findViewById(R.id.fieldContentCredit);

        //Pré rempli les champs
        fieldContentNom.setText(this.nomComplet);
        fieldContentMail.setText(this.mail);
        fieldContentCredit.setText(this.credit);
    }


    //*** Getters & Setters ***//

    public TextView getFieldContentCredit() {
        return fieldContentCredit;
    }

    public void setFieldContentCredit(TextView fieldContentCredit) {
        this.fieldContentCredit = fieldContentCredit;
    }

    public TextView getFieldContentNom() {
        return fieldContentNom;
    }

    public void setFieldContentNom(TextView fieldContentNom) {
        this.fieldContentNom = fieldContentNom;
    }

    public TextView getFieldContentMail() {
        return fieldContentMail;
    }

    public void setFieldContentMail(TextView fieldContentMail) {
        this.fieldContentMail = fieldContentMail;
    }

    //*** Méthodes ***//

    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {
            Membre membreConnecte = Connexion.getInstance().getMembreConnecte();

            this.nomComplet = membreConnecte.getPrenom() + " " + membreConnecte.getNom();
            this.mail = membreConnecte.getEmail();
            this.credit = membreConnecte.getCredit() + " crédits";
        }
    }
}
