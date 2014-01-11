package fr.if26.virtualut.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.if26.virtualut.R;
import fr.if26.virtualut.activity.CompteActivity;
import fr.if26.virtualut.activity.TransactionActivity;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Membre;
import fr.if26.virtualut.model.ListeMembres;
import fr.if26.virtualut.model.Transaction;
import fr.if26.virtualut.service.EffectuerVirementService;

public class TabEffectuerTransactionFragment extends Fragment implements View.OnClickListener {

    //*** Attributs ***//

    //Fragments
    SoldeFragment soldeFragment;

    //Vues
    private AutoCompleteTextView autoComplete;

    private TextView textView_destinataire;
    private TextView textView_montant;
    private TextView textView_libelle;

    private Button button_annuler;
    private Button button_later;
    private Button button_valider;

    private Time time;
    private String today;
    private DatePickerDialog datePickerDialog = null;

    private int newTransaction_day;
    private int newTransaction_month;
    private int newTransaction_year;

    //Autres
    private List<Membre> listMembre;
    private Membre membrePicked;
    private Membre membreConnecte;
    private EffectuerVirementService effectuerVirementService;

    //*** Constructeur ***//

    public TabEffectuerTransactionFragment(){
        super();
        initialiserFragment();
    }

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_transaction, container, false);

        soldeFragment = new SoldeFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.soldeFragment, soldeFragment).commit();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Récupère les vues
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

        //Création du champ d'auto completion
        autoComplete = (AutoCompleteTextView) getActivity().findViewById(R.id.transaction_destinataire);
        autoComplete.setAdapter(new ArrayAdapter<Membre>(this.getActivity(),
                android.R.layout.simple_list_item_1, listMembre));

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                membrePicked = ListeMembres.getInstance().getMembreFromName(textView.getText().toString());
            }
        });
    }

    //*** Méthodes ***//


    public void initialiserFragment() {

        if(Connexion.getInstance().isConnecte()) {
            membreConnecte = Connexion.getInstance().getMembreConnecte();
            listMembre = ListeMembres.getInstance().getListeMembres();

            //Chargement de la date du jour
            time = new Time(Time.getCurrentTimezone());
            time.setToNow();
            today=time.format("%d/%m/%Y");
        }
    }

    /**
     * Lance un processus parallèle ajoutant une transaction
     * @param idReceiver
     * @param token
     * @param montant
     * @param libelle
     * @param valide
     * @return
     */
    public boolean attemptEffectuerTransaction(String idReceiver, String token, String montant, String date, String libelle, String valide) {

        if(valide.equals("0")) { //Si on a une transaction a effectué, on a besoin de la date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = null;

            try {
                myDate = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Enlève les 3 derniers caractères (000 buggués)
            date = String.valueOf(myDate.getTime());
            date = date.substring(0,date.length()-3);
        }

        //Valeur de retour
        Boolean success = false;

        //Vérifie la connexion à internet
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Effectue la requête
        if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {

            effectuerVirementService = new EffectuerVirementService();
            EffectuerVirementService.EffectuerVirementTask effectuerVirementTask = effectuerVirementService.newEffectuerVirementTask();
            effectuerVirementTask.execute(idReceiver,token,montant,date,libelle,valide);

            try {
                success = effectuerVirementTask.get(20000, TimeUnit.MILLISECONDS);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        else { //Pas de connexion
            Toast.makeText(this.getActivity(),R.string.login_error_noconnection,Toast.LENGTH_LONG).show();
        }

        return success;
    }

    @Override
    public void onClick(View v) {

        Boolean error = false;
        //Vérifie si les champs ont bien été remplis par l'utilisateur (sauf si on annule)

        if(v.getId() != R.id.button_annuler) {
            if(TextUtils.isEmpty(textView_destinataire.getEditableText())){
                textView_destinataire.setError(getString(R.string.nouveauvirement_erreur_destinataire_vide));
                textView_destinataire.requestFocus();
                error = true;
            }
            else if(TextUtils.isEmpty(textView_montant.getEditableText())){
                textView_montant.setError(getString(R.string.nouveauvirement_erreur_montant_vide));
                textView_montant.requestFocus();
                error = true;
            }
            else if(Double.parseDouble(textView_montant.getText().toString()) > membreConnecte.getCredit() ){
                textView_montant.setError(getString(R.string.nouveauvirement_erreur_credit_faible));
                textView_montant.requestFocus();
                error = true;
            }
            else if(Double.parseDouble(textView_montant.getText().toString()) == 0){
                textView_montant.setError(getString(R.string.nouveauvirement_erreur_montant_negatif));
                textView_montant.requestFocus();
                error = true;

            }
            else if(ListeMembres.getInstance().getMembreFromName(textView_destinataire.getText().toString()) == null) {
                textView_destinataire.setError(getString(R.string.nouveauvirement_erreur_destinataire_inconnu));
                textView_destinataire.requestFocus();
                error = true;
            }
        }
        else {
            error = false; //Jamais d'erreur si on annule
        }

        //Création de l'alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(true);

        switch(v.getId()){

            case R.id.button_annuler:
                builder.setTitle(R.string.nouveauvirement_alertdialog_annuler_title);
                builder.setMessage(R.string.nouveauvirement_alertdialog_annuler_message);
                builder.setPositiveButton(getString(R.string.yes), new OkAnnulerListener());
                builder.setNegativeButton(getString(R.string.no), new NoAnnulerListener());

                break;

            case R.id.button_later:
                builder.setTitle(getString(R.string.nouveauvirement_alertdialog_later_title));
                builder.setMessage(getString(R.string.nouveauvirement_alertdialog_later_message));
                builder.setPositiveButton(getString(R.string.yes), new OkLaterListener());
                builder.setNegativeButton(getString(R.string.no), new NoLaterListener());
                break;

            case R.id.button_valider:
                builder.setTitle(getString(R.string.nouveauvirement_alertdialog_valider_title));
                builder.setMessage(getString(R.string.nouveauvirement_alertdialog_valider_message));
                builder.setPositiveButton(getString(R.string.yes), new OkValiderListener());
                builder.setNegativeButton(getString(R.string.no), new NoValiderListener());
                break;
        }

        //Affichage de l'alert dialog
        if(!error) {
            builder.create().show();
        }
    }

    //*** Getters et setters***//

    public void setTextView_destinataire (String tv){
        this.textView_destinataire.setText(tv);
    }
    public void setTextView_montant (int tv){
        this.textView_montant.setText(String.valueOf(tv));
    }
    public void setTextView_libelle (String tv){
        this.textView_libelle.setText(tv);
    }

    public EffectuerVirementService getEffectuerVirementService() {
        return effectuerVirementService;
    }

    public void setEffectuerVirementService(EffectuerVirementService effectuerVirementService) {
        this.effectuerVirementService = effectuerVirementService;
    }

    //*** Listeners internes ***//

    //Listener du bouton annuler

    private final class NoAnnulerListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), getString(R.string.nouveauvirement_alertdialog_annuler_cancelled),
                    Toast.LENGTH_LONG).show();
        }
    }

    private final class OkAnnulerListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), getString(R.string.nouveauvirement_alertdialog_annuler_confirmed),
                    Toast.LENGTH_LONG).show();
            textView_destinataire.setText("");
            textView_libelle.setText("");
            textView_montant.setText("");
        }
    }

    //Listener du bouton plus tard

    private final class NoLaterListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), getString(R.string.nouveauvirement_alertdialog_later_cancelled),
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

    //Listener du bouton valider

    private final class NoValiderListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), getString(R.string.nouveauvirement_alertdialog_valider_cancelled),
                    Toast.LENGTH_LONG).show();

        }
    }

    private final class OkValiderListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            Boolean success = false;

            if(membrePicked != null) {
                success = attemptEffectuerTransaction(String.valueOf(membrePicked.getId()),Connexion.getInstance().getToken(),textView_montant.getText().toString(), null, textView_libelle.getText().toString(),String.valueOf(1));
            }

            if(success) {
                Toast.makeText(getActivity(), getString(R.string.nouveauvirement_alertdialog_valider_confirmed) + " " + textView_destinataire.getText().toString() + " de " + textView_montant.getText().toString() + " crédit(s), le " + today + ".",
                        Toast.LENGTH_LONG).show();

                //Retour à la page compte pour mettre à jour la liste des transactions

                Intent intent = new Intent(getActivity(), CompteActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();
            }
        }
    }


    //Listener du DatePickerDialog

    private class PickDate implements DatePickerDialog.OnDateSetListener {

        boolean instance = false;

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

            //On utilise ce booleen pour éviter que la méthode se lance deux fois
            if(instance == true) {
                return;
            }
            else {
                //first instance
                instance = true;
            }
            datePickerDialog.hide();

            monthOfYear++;

            //On teste si la date est antérieure à la date actuelle
            if((year < Integer.parseInt(time.format("%Y"))) || (monthOfYear < Integer.parseInt(time.format("%m")) && year == Integer.parseInt(time.format("%Y"))) || (dayOfMonth < Integer.parseInt(time.format("%d")) && monthOfYear == Integer.parseInt(time.format("%m")) && year == Integer.parseInt(time.format("%Y")))  ){
                Toast.makeText(getActivity(), getString(R.string.nouveauvirement_erreur_date_passee),Toast.LENGTH_LONG).show();
            }
            else {

                Boolean success = false;

                if(membrePicked != null) {

                    String month = "" + monthOfYear;
                    if(monthOfYear < 10) {
                        month = "0" + monthOfYear;
                    }

                    String day = "" + dayOfMonth;
                    if(dayOfMonth < 10) {
                        day = "0" + dayOfMonth;
                    }

                    success = attemptEffectuerTransaction(String.valueOf(membrePicked.getId()),Connexion.getInstance().getToken(),textView_montant.getText().toString(), year + "-" + month + "-" + day, textView_libelle.getText().toString(),String.valueOf(0));
                }
                if(success) {

                    newTransaction_day=dayOfMonth;
                    newTransaction_month=monthOfYear;
                    newTransaction_year=year;

                    //Création d'une notification
                    newNotification();

                    //Affichage du toast
                    Toast.makeText(getActivity(), getString(R.string.nouveauvirement_alertdialog_later_confirmed)+ " " + dayOfMonth+"/"+monthOfYear+"/"+year,Toast.LENGTH_LONG).show();

                    //On ajoute la transaction dans la liste à effectuer

                    TransactionActivity activity = (TransactionActivity) getActivity();
                    List<Fragment> fragmentList =  activity.getFragments();
                    TabPlusTardFragment fg = (TabPlusTardFragment) fragmentList.get(1);

                    Transaction nouvelleTransaction = new Transaction();
                    nouvelleTransaction.setLibelle(textView_libelle.getText().toString());
                    nouvelleTransaction.setMontant(Integer.parseInt(textView_montant.getText().toString()));
                    nouvelleTransaction.setDate(year+"-"+monthOfYear+"-"+dayOfMonth);
                    nouvelleTransaction.setSender(membreConnecte);
                    nouvelleTransaction.setReceiver(membrePicked);

                    textView_destinataire.setText("");
                    textView_libelle.setText("");
                    textView_montant.setText("");

                    fg.addTransaction(nouvelleTransaction);
                    ViewPager pager = activity.getPager();
                    pager.setCurrentItem(1);
                }
            }
        }
    }

    /**
     * Créer une notification
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void newNotification() {
        //On utilise la date pour donner un identifiant unique à la notification
        Date now = new Date();
        long uniqueId = now.getTime();

        String msgText = "Rappel transaction." +
                            "\nDestinataire: "+textView_destinataire.getText().toString()+
                            "\nMontant: "+textView_montant.getText().toString()+
                            "\nLibellé: "+textView_libelle.getText().toString()+
                            "\nDate: "+newTransaction_day+"/"+newTransaction_month+"/"+newTransaction_year;

        //On crée l'intent qui va se lancer une fois qu'on aura cliqué sur la notification
        Intent intent = new Intent(getActivity().getApplicationContext(),TransactionActivity.class);

        //On crée le notificationmanager qui gère toutes les notifications
        NotificationManager notificationManager = (NotificationManager)getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //On crée la nouvelle notification
        PendingIntent pi = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext());
        builder.setContentTitle("Notification VirtualUT")
                .setContentText(msgText)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msgText))
                .addAction(R.drawable.abc_ic_go_search_api_holo_light, "Effectuer transaction", pi);

        Notification notification = builder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //On l'ajoute dans le notification manager, avec en premier paramètre son id unique
        notificationManager.notify((int)uniqueId, notification);
    }

}
