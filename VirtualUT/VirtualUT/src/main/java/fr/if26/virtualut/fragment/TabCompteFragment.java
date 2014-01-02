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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;
import fr.if26.virtualut.model.Transaction;


public class TabCompteFragment extends Fragment implements View.OnClickListener{

    //*** Attributs ***//

    //Fragments
    ListFragment listFragment;
    SoldeFragment soldeFragment;

    //Vues
    private Button buttonTriDate;
    private Button buttonTriMontant;

    private boolean dateTrier=false;
    private boolean montantTrier=false;

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

        buttonTriDate.setOnClickListener(this);
        buttonTriMontant.setOnClickListener(this);
    }

    //*** Méthodes ***//

    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {

        }
    }


    public void onClick(View v) {


        ArrayAdapter<Transaction> arrayAdapter =  listFragment.getContactsAdapter();
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList = listFragment.getTransactionList();

        switch(v.getId()){

            case R.id.buttonTriDate:

                if(dateTrier==false){

                Collections.sort(transactionList, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction tr1, Transaction tr2) {
                        String date1=tr1.getDate();
                        String date2=tr2.getDate();

                        return date1.compareTo(date2);
                    }
                });
                    dateTrier=true;
                }
                else{
                    Collections.sort(transactionList, new Comparator<Transaction>() {
                        @Override
                        public int compare(Transaction tr1, Transaction tr2) {
                            String date1=tr1.getDate();
                            String date2=tr2.getDate();

                            return date2.compareTo(date1);
                        }
                    });
                    dateTrier=false;
                }
                Toast.makeText(getActivity(), "Tri par date",
                        Toast.LENGTH_LONG).show();
                listFragment.setTransactionList(transactionList);
                arrayAdapter.notifyDataSetChanged();


                break;

            case R.id.buttonTriMontant:

                if(montantTrier==false){

                    Collections.sort(transactionList, new Comparator<Transaction>() {
                        @Override
                        public int compare(Transaction tr1, Transaction tr2) {
                            String montant1=String.valueOf(tr1.getMontant());
                            String montant2=String.valueOf(tr2.getMontant());

                            return montant1.compareTo(montant2);
                        }
                    });
                    montantTrier=true;

                }

                else{
                    Collections.sort(transactionList, new Comparator<Transaction>() {
                        @Override
                        public int compare(Transaction tr1, Transaction tr2) {
                            String montant1=String.valueOf(tr1.getMontant());
                            String montant2=String.valueOf(tr2.getMontant());

                            return montant2.compareTo(montant1);
                        }
                    });
                    montantTrier=false;

                }

                Toast.makeText(getActivity(), "Tri par montant",
                        Toast.LENGTH_LONG).show();
                listFragment.setTransactionList(transactionList);
                arrayAdapter.notifyDataSetChanged();



                break;
        }

    }
}
