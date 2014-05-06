package com.exapmle.adapter;

import com.example.sebiapplication.indices;
import com.example.sebiapplication.learnMore;
import com.example.sebiapplication.learning_center;
import com.example.sebiapplication.marketMovers;
import com.example.sebiapplication.News;
import com.example.sebiapplication.trading;

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
