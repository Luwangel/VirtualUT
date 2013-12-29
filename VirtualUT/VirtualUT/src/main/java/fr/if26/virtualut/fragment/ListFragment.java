package fr.if26.virtualut.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;
import fr.if26.virtualut.model.Transaction;

/**
 * Fragment gérant une liste d'objets de type transaction
 */
public class ListFragment extends android.support.v4.app.ListFragment {

    //*** Attributs ***//

    ArrayList<Transaction> transactionList;

    //*** Constructeur ***//

    public ListFragment(ArrayList<Transaction> transactionList) {
        this();
        this.setTransactionList(transactionList);
    }

    public ListFragment() {
        super();
        transactionList = new ArrayList<Transaction>();
    }

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.setListAdapter(new ContactsAdapter(this.getActivity(), transactionList));

    }

    //*** Adapter de la liste ***//

    private class ContactsAdapter extends ArrayAdapter<Transaction>
    {
        public ContactsAdapter(Context context, ArrayList<Transaction> transactions) {
            super(context, R.layout.fragment_list, transactions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_list, null);

            ((TextView) viewGroup.findViewById(R.id.date)).setText(this.getItem(position).getDate());

            ((TextView) viewGroup.findViewById(R.id.libelle)).setText(this.getItem(position).getLibelle());
            ((TextView) viewGroup.findViewById(R.id.libelle)).setSelected(true);
            if (this.getItem(position).getReceiver().getId() == Connexion.getInstance().getMembreConnecte().getId() ){
                ((TextView) viewGroup.findViewById(R.id.montant)).setText("+" + String.valueOf(this.getItem(position).getMontant())+" cr.");
                ((TextView) viewGroup.findViewById(R.id.montant)).setTextColor(Color.parseColor("#59ab39"));
            }
            else{
                ((TextView) viewGroup.findViewById(R.id.montant)).setText(String.valueOf(this.getItem(position).getMontant())+" cr.");
                ((TextView) viewGroup.findViewById(R.id.montant)).setTextColor(Color.parseColor("#cc5050"));
            }

            return viewGroup;
        }
    }

    //*** Getters & Setters ***//

    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
