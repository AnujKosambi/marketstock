package com.marketstock.adapter;

import com.marketstock.sebiapplication.learning_center;
import com.marketstock.sebiapplication.trading;

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
            return new learning_center();
        case 1:
            return new trading();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
