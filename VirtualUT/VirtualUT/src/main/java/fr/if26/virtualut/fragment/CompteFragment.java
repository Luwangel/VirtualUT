package fr.if26.virtualut.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fr.if26.virtualut.R;

/**
 * Created by Luwangel on 09/12/13.
 */
public class CompteFragment extends Fragment {


    public static final String TAG = "CompteFragment";

    private String title;

    /**
     * Permet de contrôler l'instance de la classe
     * @param title
     * @return
     */
    public static CompteFragment newInstance(String title) {
        CompteFragment fragment = new CompteFragment();
        fragment.setTitle(title);

        return fragment;
    }

    private CompteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compte, container, false);
    }

    /* Getter et Setters */

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}