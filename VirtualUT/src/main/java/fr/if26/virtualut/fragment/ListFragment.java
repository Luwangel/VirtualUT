package fr.if26.virtualut.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import fr.if26.virtualut.R;

/**
 * Created by Luwangel on 29/10/13.
 */
public class ListFragment extends android.support.v4.app.ListFragment {

    public static final String TAG = "ListFragment";

    /**
     * Le titre du fragment
     */
    private String title;

    /**
     * Contient la liste à afficher dans le fragment
     */
    private String[] liste;

    /**
     * Permet de contrôler l'instance de la classe
     * @param title
     * @return
     */
    public static ListFragment newInstance(String title) {
        ListFragment fragment = new ListFragment(null);
        fragment.setTitle(title);
        return fragment;
    }

    /**
     * Constructeur privé
     * @param liste
     */
    private ListFragment(String[] liste) {
        this.setListe(liste);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(this.liste != null) {
            ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, this.liste);
            setListAdapter(aa);
        }
    }

    /**
     * Action effectuée lors du clic d'un item de la liste
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //String s = (String) l.getItemAtPosition(position);
    }

/* Getters et Setters */

    public void setListe(String[] liste) {
        this.liste = liste;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}