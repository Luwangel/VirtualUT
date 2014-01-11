package fr.if26.virtualut.fragment;

/**
 * Created by Thanh-Tuan on 14/12/13.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.if26.virtualut.R;
import fr.if26.virtualut.model.Connexion;
import fr.if26.virtualut.model.Transaction;
import fr.if26.virtualut.service.RecupererVirementsService;

public class TabPlusTardFragment extends Fragment {

    //*** Attributs ***//

    //Fragments
    private ListFragment listTransaction;

    //Autre
    private RecupererVirementsService recupererVirementsService;

    //*** Constructeur ***//

    public TabPlusTardFragment(){
        super();
    }

    //*** Implémentation des méthodes du fragment ***//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_effectuer, container, false);

        //Récupère la liste
        attemptRecupererVirementsAEffectuer(Connexion.getInstance().getToken());

        //Fragment
        listTransaction = new ListFragment(Connexion.getInstance().getMembreConnecte().getTransactionsAEffectuer());

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.listTransaction, listTransaction);
        transaction.commit();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //*** Getters & Setters ***//

    public ListFragment getListTransaction(){ return this.listTransaction; }

    public void addTransaction(Transaction tr){
        getListTransaction().getTransactionList().add(tr);
    }

    //*** Récupération de la liste ***//

    public boolean attemptRecupererVirementsAEffectuer(String token) {

        //Valeur de retour
        Boolean success = false;

        //Vérifie la connexion à internet
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Effectue la requête
        if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {

            recupererVirementsService = new RecupererVirementsService();
            RecupererVirementsService.RecupererVirementsTask recupererVirementsTask = recupererVirementsService.newRecupererVirementsTask();
            recupererVirementsTask.execute("0",token);

            try {
                success = recupererVirementsTask.get(20000, TimeUnit.MILLISECONDS);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        else { //Pas de connexion
            Toast.makeText(this.getActivity(), R.string.login_error_noconnection, Toast.LENGTH_LONG).show();
        }

        return success;
    }
}
