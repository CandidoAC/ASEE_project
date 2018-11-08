package com.example.usuario.projectasee;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuToolbar extends FragmentPagerAdapter {

    private final List <Fragment> mFragmentList = new ArrayList <> ();
    private final List<String> mFragmentTitleList = new ArrayList<> ();


    //este metodo anade fragmentos a la lista de fragmentos
    public void addFragment(Fragment fragment , String title) {
        mFragmentList.add ( fragment );
        mFragmentTitleList.add ( title );
    }

    public MenuToolbar(FragmentManager fm) {
        super ( fm );
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get ( position );
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get ( position );
    }

    @Override
    public int getCount() {
        return mFragmentList.size ();
    }
}