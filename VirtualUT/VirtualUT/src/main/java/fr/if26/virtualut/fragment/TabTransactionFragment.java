package fr.if26.virtualut.fragment;

/**
 * Created by Thanh-Tuan on 14/12/13.
 */


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.if26.virtualut.R;
import fr.if26.virtualut.activity.CompteActivity;
import fr.if26.virtualut.activity.TransactionActivity;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;

public class TabTransactionFragment extends Fragment implements View.OnClickListener {


    private AutoCompleteTextView autoComplete;

    private TextView textViewNom_transaction;
    private TextView textViewSolde_transaction;
    private TextView textViewCredit_transaction;

    private TextView textView_destinataire;
    private TextView textView_montant;
    private TextView textView_libelle;

    private String nomComplet;
    private String credit;
    private Button button_annuler;
    private Button button_later;
    private Button button_valider;
    private ArrayList<String> listMembre;

    private Time time;
    private String today;
    private DatePickerDialog datePickerDialog = null;

    private Membre membreConnecte;

    //*** Constructeur ***//

    public TabTransactionFragment (){
        super();
        initialiserFragment();
    }

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_transaction, container, false);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Récupère les vues
        textViewNom_transaction = (TextView) getActivity().findViewById(R.id.textViewNom_transaction);
        textViewCredit_transaction = (TextView) getActivity().findViewById(R.id.textViewCredit_transaction);
        textView_destinataire = (TextView) getActivity().findViewById(R.id.transaction_destinataire);
        textView_libelle = (TextView) getActivity().findViewById(R.id.transaction_libelle);
        textView_montant = (TextView) getActivity().findViewById(R.id.transaction_montant);
        button_annuler = (Button) getActivity().findViewById(R.id.button_annuler);
        button_later = (Button) getActivity().findViewById(R.id.button_later);
        button_valider = (Button) getActivity().findViewById(R.id.button_valider);

        //Mise en place des listeners des boutons
        button_annuler.setOnClickListener(this);
        button_later.setOnClickListener(this);
        button_valider.setOnClickListener(this);

        //Chargement de la date du jour
        textViewSolde_transaction = (TextView) view.findViewById(R.id.textViewSolde_transaction);
        time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        textViewSolde_transaction.setText("Solde au "+time.format("%d/%m/%Y")+": ");

        //Pré rempli les champs
        textViewNom_transaction.setText(this.nomComplet);
        textViewCredit_transaction.setText(this.credit);


        //Création du champ d'auto completion
        autoComplete = (AutoCompleteTextView) getActivity().findViewById(R.id.transaction_destinataire);
        autoComplete.setAdapter(new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, listMembre));

    }





    //*** Méthodes ***//


    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {
            membreConnecte = Connexion.getInstance().getMembreConnecte();

            this.nomComplet = membreConnecte.getPrenom() + " " + membreConnecte.getNom();
            this.credit = membreConnecte.getCredit() + " crédits";

            //Remplissage de la liste pour autocomplétion
            listMembre = new ArrayList<String>();
            listMembre.add("Thanh-Tuan TRINH");
            listMembre.add("Thanh TRINH");
            listMembre.add("Alexandre DUPONT");
            listMembre.add("Alexandre DURANT");
            listMembre.add("Alain DURANT");

            //Chargement de la date du jour
            time = new Time(Time.getCurrentTimezone());
            time.setToNow();
            today=time.format("%d/%m/%Y");
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){


            case R.id.button_annuler:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                builder.setTitle("Annuler");
                builder.setMessage("Toutes les informations vont être supprimées. Voulez-vous vraiment annuler la transaction ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Oui", new OkAnnulerListener());
                builder.setNegativeButton("Non", new NoAnnulerListener());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;


            case R.id.button_later:
                //***Vérifie si les champs ont bien été remplis par l'utilisateur***//

                if(TextUtils.isEmpty(textView_destinataire.getEditableText())){
                    textView_destinataire.setError("Veuillez entrer un destinataire.");
                    textView_destinataire.requestFocus();
                }
                else if(TextUtils.isEmpty(textView_montant.getEditableText())){
                    textView_montant.setError("Veuillez entrer un montant.");
                    textView_montant.requestFocus();

                }
                else if(Double.parseDouble(textView_montant.getText().toString()) > membreConnecte.getCredit() ){
                    textView_montant.setError("Veuillez entrer un montant inférieur à votre crédit.");
                    textView_montant.requestFocus();
                }
                else if(Double.parseDouble(textView_montant.getText().toString()) == 0){
                    textView_montant.setError("Veuillez entrer un montant supérieur à 0.");
                    textView_montant.requestFocus();

                }
                else if(!listMembre.contains(textView_destinataire.getText().toString())){
                    textView_destinataire.setError("Le destinataire n'existe pas. Veuillez choisir un autre destinaire.");
                    textView_destinataire.requestFocus();

                }
                //***Si les champs ont bien été remplis, boite de dialogue apparait***//

                else{
                    builder = new AlertDialog.Builder(this.getActivity());
                    builder.setTitle("Plus tard");
                    builder.setMessage("Voulez-vous vraiment effectuer la transaction plus tard ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Oui", new OkLaterListener());
                    builder.setNegativeButton("Non", new NoLaterListener());
                    dialog = builder.create();
                    dialog.show();
                }
                break;


            case R.id.button_valider:

            //***Vérifie si les champs ont bien été remplis par l'utilisateur***//

                if(TextUtils.isEmpty(textView_destinataire.getEditableText())){
                textView_destinataire.setError("Veuillez entrer un destinataire.");
                textView_destinataire.requestFocus();
                }
                else if(TextUtils.isEmpty(textView_montant.getEditableText())){
                textView_montant.setError("Veuillez entrer un montant.");
                textView_montant.requestFocus();

                }
                else if(Double.parseDouble(textView_montant.getText().toString()) > membreConnecte.getCredit() ){
                textView_montant.setError("Veuillez entrer un montant inférieur à votre crédit.");
                textView_montant.requestFocus();
                }
                else if(Double.parseDouble(textView_montant.getText().toString()) == 0){
                textView_montant.setError("Veuillez entrer un montant supérieur à 0.");
                textView_montant.requestFocus();

                }
                else if(!listMembre.contains(textView_destinataire.getText().toString())){
                textView_destinataire.setError("Le destinataire n'existe pas. Veuillez choisir un autre destinaire.");
                textView_destinataire.requestFocus();

                }

            //***Si les champs ont bien été remplis, boite de dialogue apparait***//

                else{
                builder = new AlertDialog.Builder(this.getActivity());
                builder.setTitle("Valider");
                builder.setMessage("Voulez-vous vraiment effectuer la transaction ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Oui", new OkValiderListener());
                builder.setNegativeButton("Non", new NoValiderListener());
                dialog = builder.create();
                dialog.show();
                }
                break;
        }

    }






    //***Listener du bouton annuler***//

    private final class NoAnnulerListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "Transaction non annulée",
                    Toast.LENGTH_LONG).show();

        }
    }

    private final class OkAnnulerListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "Transaction annulée",
                    Toast.LENGTH_LONG).show();
            textView_destinataire.setText("");
            textView_libelle.setText("");
            textView_montant.setText("");
        }
    }





    //***Listener du bouton plus tard***//

    private final class NoLaterListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "Transaction non effectuée",
                    Toast.LENGTH_LONG).show();

        }
    }

    private final class OkLaterListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            //***Initialisation du jour, mois, année***//
            int day = Integer.parseInt(time.format("%d"));
            int month = Integer.parseInt(time.format("%m"));
            int year = Integer.parseInt(time.format("%Y"));


            //***Apparition du datePickerDialog***//
            datePickerDialog = new DatePickerDialog(getActivity(), new PickDate(), year, month-1, day);
            datePickerDialog.updateDate(year, month-1, day);
            datePickerDialog.show();


        }
    }




    //***Listener du bouton valider***//

    private final class NoValiderListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "Transaction non effectuée",
                    Toast.LENGTH_LONG).show();

        }
    }

    private final class OkValiderListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {


            Toast.makeText(getActivity(), "Transaction effectuée à "+textView_destinataire.getText().toString()+" de "+textView_montant.getText().toString()+" crédit(s), le "+today+".",
                    Toast.LENGTH_LONG).show();


            //***Retour à la page compte pour mettre à jour la liste des transactions***//

            Intent intent = new Intent(getActivity(), CompteActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
            getActivity().finish();
        }
    }


    //***Listener du DatePickerDialog***//

    private class PickDate implements DatePickerDialog.OnDateSetListener {


        boolean instance = false;

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            //***On utilise ce booleen pour éviter que la méthode se lance deux fois***//
            if (instance == true) {
                return;
            } else {
                //first instance
                instance = true;
            }
            datePickerDialog.hide();

            monthOfYear++;

            //***On teste si la date est antérieure à la date actuelle***//
            if( (year < Integer.parseInt(time.format("%Y"))) || (monthOfYear < Integer.parseInt(time.format("%m")) && year == Integer.parseInt(time.format("%Y"))) || (dayOfMonth < Integer.parseInt(time.format("%d")) && monthOfYear == Integer.parseInt(time.format("%m")) && year == Integer.parseInt(time.format("%Y")))  ){
            Toast.makeText(getActivity(), "Erreur, la date choisie est antérieure à la date actuelle. Veuillez choisir une autre date.",
                   Toast.LENGTH_LONG).show();
            }


            else{
            Toast.makeText(getActivity(), "Transaction ajoutée pour le "+dayOfMonth+"/"+monthOfYear+"/"+year,
                    Toast.LENGTH_LONG).show();


            //***Rafraichissement de la page Transaction pour mettre à la jour la liste des transactions à effectuer***//

            Intent intent = new Intent(getActivity(), TransactionActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
            getActivity().finish();
            }

        }

    }


}
