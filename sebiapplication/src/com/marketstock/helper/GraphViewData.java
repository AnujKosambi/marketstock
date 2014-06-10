package com.marketstock.helper;


import java.util.Date;

import android.text.format.Time;

import com.jjoe64.graphview.GraphViewDataInterface;
import com.marketstock.sebiapplication.stockChart;

public class GraphViewData implements GraphViewDataInterface  {
    private double x,y;
    private boolean isnews;

    public GraphViewData(double x, double y,boolean isnews)  {
      
    	//this.x=x;
    	this.isnews=isnews;
        this.x = (double)((long)(x));
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

	@Override
	public boolean isNews(double arg0) {
		if(isnews)
		return true;
		else
		return false;
		
	}


}
