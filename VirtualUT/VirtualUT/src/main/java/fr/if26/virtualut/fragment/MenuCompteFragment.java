package fr.if26.virtualut.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import fr.if26.virtualut.R;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class MenuCompteFragment extends Fragment implements View.OnClickListener {

    public static final int COMPTE = 0;
    public static final int PROFIL = 1;

    private String title;
    private Button compte;
    private Button profil;
    public static final String TAG = "MenuCompteFragment";


    public static MenuCompteFragment newInstance(String title) {
        MenuCompteFragment fragment = new MenuCompteFragment();
        fragment.setTitle(title);

        return fragment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.submenu_compte, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.profil = (Button) getView().findViewById(R.id.right_compte);
        this.profil.setOnClickListener(this);
        this.compte = (Button) getView().findViewById(R.id.left_compte);
        this.compte.setOnClickListener(this);
    }

    public void setActive(int tab)
    {
        this.getView().findViewById(R.id.left_compte).setBackgroundColor(getResources().getColor(R.color.submenu_background));
        this.getView().findViewById(R.id.right_compte).setBackgroundColor(getResources().getColor(R.color.submenu_background));

        switch(tab)
        {
            case COMPTE:
                this.getView().findViewById(R.id.left_compte).setBackgroundColor(getResources().getColor(R.color.submenu_active_line));
                setListeners(MenuCompteFragment.COMPTE);
                break;
            case PROFIL:
                this.getView().findViewById(R.id.right_compte).setBackgroundColor(getResources().getColor(R.color.submenu_active_line));
                setListeners(MenuCompteFragment.PROFIL);
                break;
        }
    }

    public void setListeners(int tab)
    {
        switch(tab)
        {
            case COMPTE:
                this.getView().findViewById(R.id.right_compte).setOnClickListener(this);
                break;
            case PROFIL:
                this.getView().findViewById(R.id.left_compte).setOnClickListener(this);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.right_compte:
                setActive(PROFIL);
                Toast.makeText(getActivity(), "profil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_compte:
                setActive(COMPTE);
                Toast.makeText(getActivity(), "compte", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
