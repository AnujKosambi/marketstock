package com.exapmle.adapter;

import com.example.sebiapplication.indices;
import com.example.sebiapplication.marketMovers;
import com.example.sebiapplication.portFolio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new indices();
        case 1:
            // Games fragment activity
            return new marketMovers();
        case 2:
            // Movies fragment activity
            return new portFolio();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
