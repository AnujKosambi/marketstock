package com.exapmle.adapter;

import com.example.sebiapplication.indices;
import com.example.sebiapplication.learnMore;
import com.example.sebiapplication.marketMovers;
import com.example.sebiapplication.News;

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
            return new indices();
        case 1:
            return new marketMovers();
        case 2:
            return new News();
        case 3:
        	return new learnMore();
        	
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
