package fr.if26.virtualut.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.if26.virtualut.R;
import fr.if26.virtualut.activity.TransactionActivity;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Transaction;

/**
 * Fragment gérant une liste d'objets de type transaction
 */
public class ListFragment extends android.support.v4.app.ListFragment {

    //*** Attributs ***//

    List<Transaction> transactionList;
    private boolean effectuer=true;
    private Transaction itemClick;
    private int positionItem;
    private ContactsAdapter contactsAdapter;

    //*** Constructeur ***//

    public ListFragment(List<Transaction> transactionList) {
        this();
        this.setTransactionList(transactionList);
    }

    public ListFragment() {
        super();
        transactionList  = new ArrayList<Transaction>();
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
        public ContactsAdapter(Context context, List<Transaction> transactions) {
            super(context, R.layout.fragment_list, transactions);
            contactsAdapter = this;
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //on vérifie si la liste des transactions a été effectué ou non

        if(!getEffectuer()){
        itemClick = (Transaction)l.getItemAtPosition(position);
        positionItem = position;


            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("Transaction");
            builder.setMessage("Veuillez choisir une action à effectuer.");
            builder.setCancelable(true);
            builder.setPositiveButton("Effectuer transaction", new EffectuerButtonListener());
            builder.setNeutralButton("Supprimer transaction", new SupprimerButtonListener());

            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    //*** Listener des boutons de la dialogbox ***//

    private final class EffectuerButtonListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            TransactionActivity activity = (TransactionActivity) getActivity();
            ViewPager pager = activity.getPager();
            pager.setCurrentItem(0);
            List<Fragment> fragmentList =  activity.getFragments();
            TabEffectuerTransactionFragment fg = (TabEffectuerTransactionFragment) fragmentList.get(0);
            fg.setTextView_destinataire(itemClick.getReceiver().getPrenom()+" "+itemClick.getReceiver().getNom());
            fg.setTextView_libelle(itemClick.getLibelle());
            fg.setTextView_montant(itemClick.getMontant());

        }
    }

    private final class SupprimerButtonListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            transactionList.remove(positionItem);
            Toast.makeText(getActivity(), "Transaction supprimée",
                    Toast.LENGTH_LONG).show();


            TransactionActivity activity = (TransactionActivity) getActivity();
            ViewPager pager = activity.getPager();
            pager.setCurrentItem(0);

        }
    }

    //*** Getters & Setters ***//

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public void setEffectuer(boolean effectuer){

        this.effectuer=effectuer;

    }

    public boolean getEffectuer(){
        return this.effectuer;
    }

    public ContactsAdapter getContactsAdapter(){ return  this.contactsAdapter;}




}
