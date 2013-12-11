package fr.if26.virtualut.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.if26.virtualut.R;

/**
 * Created by Luwangel on 01/11/13.
 */
public class NouveauVirementFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "NouveauVirementFragment";

    private String title;
    private Button buttonSend;
    private EditText editText;

    /**
     * Permet de contrôler l'instance de la classe
     * @param title
     * @return
     */
    public static NouveauVirementFragment newInstance(String title) {
        NouveauVirementFragment fragment = new NouveauVirementFragment();
        fragment.setTitle(title);

        return fragment;
    }

    private NouveauVirementFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nouveauvirement, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.buttonSend = (Button) getView().findViewById(R.id.buttonSend);
        this.buttonSend.setOnClickListener(this);
    }

/* Getter et Setters */

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EditText getEditText() {
        return this.editText;
    }

    public void setTitle(EditText editText) {
        this.editText = editText;
    }

/* Evénements */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonSend:
                Toast.makeText(getActivity(),R.string.nouveauvirement_confirmation,Toast.LENGTH_SHORT).show();
                getActivity().getActionBar().selectTab(getActivity().getActionBar().getTabAt(0));

                break;
        }
    }
}