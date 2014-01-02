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

import java.util.ArrayList;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;
import fr.if26.virtualut.model.Transaction;

public class TabPlusTardFragment extends Fragment {

    //*** Attributs ***//

    private Membre membreConnecte;

    //Fragments
    private ListFragment listTransaction;

    //*** Constructeur ***//

    public TabPlusTardFragment(){
        super();
        initialiserFragment();
    }

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_effectuer, container, false);

        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

                Transaction tr1 = new Transaction();

                tr1.setDate("2013-12-12");
                tr1.setReceiver(membreConnecte);
                tr1.setSender(membreConnecte);
                tr1.setLibelle("Paiement au BDE");
                tr1.setMontant(20);

                transactionList.add(tr1);
                transactionList.add(tr1);


                listTransaction = new ListFragment(transactionList);
                listTransaction.setEffectuer(false);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.listTransaction, listTransaction);
                transaction.commit();
        return view;
    }

    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {
            membreConnecte = Connexion.getInstance().getMembreConnecte();

        }
    }

    public ListFragment getListTransaction(){ return this.listTransaction; }

    public void addTransaction(Transaction tr){
        getListTransaction().getTransactionList().add(tr);
    }

}
