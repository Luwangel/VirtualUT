package fr.if26.virtualut.compte;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Luwangel on 30/10/13.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments;

    //On fournit à l'adapter la liste des fragments à afficher
    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}