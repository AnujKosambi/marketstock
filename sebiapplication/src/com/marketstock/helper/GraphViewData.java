package com.marketstock.helper;


import java.util.Date;

import android.text.format.Time;

import com.jjoe64.graphview.GraphViewDataInterface;
import com.marketstock.sebiapplication.stockChart;

public class GraphViewData implements GraphViewDataInterface  {
    private double x,y;

    public GraphViewData(Date x, double y)  {
      
    	//this.x=x;
    	
        this.x = (double)((long)(x.getTime()/10000));
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }


}
