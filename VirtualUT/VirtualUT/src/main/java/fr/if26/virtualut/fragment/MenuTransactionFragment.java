package fr.if26.virtualut.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import fr.if26.virtualut.R;

/**
 * Created by Thanh-Tuan on 07/12/13.
 */
public class MenuTransactionFragment extends Fragment  implements OnClickListener{

    public static final int TRANSACTION = 0;
    public static final int EFFECTUER = 1;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.submenu_transaction, container, false);

        return view;
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

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.right_transaction:
                setActive(EFFECTUER);
                break;
            case R.id.left_transaction:
                setActive(TRANSACTION);
                break;
        }
    }

}

