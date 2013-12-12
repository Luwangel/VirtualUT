package fr.if26.virtualut.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import fr.if26.virtualut.R;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class MenuTransactionFragment extends Fragment implements View.OnClickListener{

    public static final int TRANSACTION = 0;
    public static final int EFFECTUER = 1;

    private String title;
    private Button transaction;
    private Button effectuer;
    public static final String TAG = "MenuTransactionFragment";

    public static MenuTransactionFragment newInstance(String title) {
        MenuTransactionFragment fragment = new MenuTransactionFragment();
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
        View view = inflater.inflate(R.layout.submenu_transaction, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.effectuer = (Button) getView().findViewById(R.id.right_transaction);
        this.effectuer.setOnClickListener(this);
        this.transaction = (Button) getView().findViewById(R.id.left_transaction);
        this.transaction.setOnClickListener(this);
    }

    public void setActive(int tab)
    {
        this.getView().findViewById(R.id.left_transaction).setBackgroundColor(getResources().getColor(R.color.submenu_background));
        this.getView().findViewById(R.id.right_transaction).setBackgroundColor(getResources().getColor(R.color.submenu_background));

        switch(tab)
        {
            case TRANSACTION:
                this.getView().findViewById(R.id.left_transaction).setBackgroundColor(getResources().getColor(R.color.submenu_active_line));
                setListeners(MenuTransactionFragment.TRANSACTION);
                break;
            case EFFECTUER:
                this.getView().findViewById(R.id.right_transaction).setBackgroundColor(getResources().getColor(R.color.submenu_active_line));
                setListeners(MenuTransactionFragment.EFFECTUER);
                break;
        }
    }

    public void setListeners(int tab)
    {
        switch(tab)
        {
            case TRANSACTION:
                this.getView().findViewById(R.id.right_transaction).setOnClickListener(this);
                break;
            case EFFECTUER:
                this.getView().findViewById(R.id.left_transaction).setOnClickListener(this);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.right_transaction:
                setActive(EFFECTUER);
                Toast.makeText(getActivity(), "effectuer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_transaction:
                setActive(TRANSACTION);
                Toast.makeText(getActivity(), "transaction", Toast.LENGTH_SHORT).show();
                //System.exit(0);
                break;
        }
    }

}

