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

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Transaction;

/**
 * Created by Administrateur on 26/12/13.
 */
public class ListFragment extends android.support.v4.app.ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Transaction tr1 = new Transaction();
        Transaction tr2 = new Transaction();
        Transaction tr3 = new Transaction();

        tr1.date="2013-12-12";
        tr1.idReceiver=1;
        tr1.idSender=2;
        tr1.libelle="Virement de Thanh";
        tr1.montant=20;

        tr2.date="2013-12-10";
        tr2.idReceiver=1;
        tr2.idSender=3;
        tr2.libelle="Virement d'Adrien";
        tr2.montant=25;

        tr3.date="2013-11-11";
        tr3.idReceiver=2;
        tr3.idSender=1;
        tr3.libelle="Paiement à Driss pour Ski UTT. Code transaction numéro 069c88069c88da1337";
        tr3.montant=10;

        transactions.add(tr1);
        transactions.add(tr2);
        transactions.add(tr3);
        transactions.add(tr1);
        transactions.add(tr2);
        transactions.add(tr3);
        transactions.add(tr1);
        transactions.add(tr2);
        transactions.add(tr3);
        transactions.add(tr1);
        transactions.add(tr2);
        transactions.add(tr3);
        transactions.add(tr1);
        transactions.add(tr2);
        transactions.add(tr3);
        transactions.add(tr1);
        transactions.add(tr2);
        transactions.add(tr3);

        this.setListAdapter(new ContactsAdapter(this.getActivity(), transactions));

    }

    private class ContactsAdapter extends ArrayAdapter<Transaction>
    {
        public ContactsAdapter(Context context, ArrayList<Transaction> transactions)
        {
            super(context, R.layout.fragment_list, transactions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_list, null);

            ((TextView) viewGroup.findViewById(R.id.date)).setText(this.getItem(position).date);

            ((TextView) viewGroup.findViewById(R.id.libelle)).setText(this.getItem(position).libelle);
            ((TextView) viewGroup.findViewById(R.id.libelle)).setSelected(true);
            if (this.getItem(position).idReceiver == 1 ){
                ((TextView) viewGroup.findViewById(R.id.montant)).setText("+" + String.valueOf(this.getItem(position).montant)+" cr.");
                ((TextView) viewGroup.findViewById(R.id.montant)).setTextColor(Color.parseColor("#59ab39"));
            }
            else{
                ((TextView) viewGroup.findViewById(R.id.montant)).setText("-" + String.valueOf(this.getItem(position).montant)+" cr.");
                ((TextView) viewGroup.findViewById(R.id.montant)).setTextColor(Color.parseColor("#cc5050"));
            }



            return viewGroup;
        }
    }
}
