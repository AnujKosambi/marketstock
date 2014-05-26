package com.marketstock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.marketstock.sebiapplication.stockChart;
import com.marketstock.sebiapplication.stockDetail;
import com.marketstock.sebiapplication.stockNews;

public class StockPagesAdapter extends FragmentPagerAdapter{

	public StockPagesAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
        case 0:
            return new stockDetail();
        case 1:
            return new stockChart();
        case 2:
            return new stockNews();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


}
