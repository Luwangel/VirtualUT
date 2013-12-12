package fr.if26.virtualut.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.if26.virtualut.R;

/**
 * Created by Thanh-Tuan on 11/12/13.
 */
public class AideFragment extends Fragment {

    public static final String TAG = "AideFragment";
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static AideFragment newInstance(String title) {
        AideFragment fragment = new AideFragment();
        fragment.setTitle(title);

        return fragment;
    }

    private AideFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_aide, container, false);
    }
}
