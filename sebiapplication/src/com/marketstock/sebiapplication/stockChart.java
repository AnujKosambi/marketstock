package com.marketstock.sebiapplication;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.marketstock.sebiapplication.models.Stock;

public class stockChart extends SherlockFragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.stock_chart, container, false);
        GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
        
	    GraphViewSeries series = new GraphViewSeries("Graph_Title", seriesStyle, new GraphViewData[] {});
       	LineGraphView graphView = new LineGraphView(this.getActivity(), "Quote");
       	graphView.addSeries(series); // data
     	graphView.setViewPort(2, 6);
       	graphView.setScrollable(true);
       	graphView.setScalable(true);
       	graphView.getGraphViewStyle().setVerticalLabelsWidth(30);
      // 	graphView.setVerticalLabels(new String[] {"high", "middle", "low"});
       	graphView.getGraphViewStyle().setGridColor(Color.GRAY);
       	graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
       	graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
       	graphView.getGraphViewStyle().setTextSize(10);
     	graphView.getGraphViewStyle().setNumHorizontalLabels(5);
       //	graphView.getGraphViewStyle().setNumVerticalLabels(4);
       //	graphView.getGraphViewStyle().setVerticalLabelsWidth(20);
       //	graphView.getGraphViewStyle().
       	graphView.setDrawBackground(true);
       	//graphView.
       	
       	
       	graphView.setBackgroundColor(Color.rgb(194, 223, 255));
        graphView.setDrawDataPoints(true);
       	graphView.setDataPointsRadius(2f);
  
		Date fromDate =new Date(1,1,2014);
       	Date toDate=new Date(31,12,2014);
   //    	graphView.setHorizontalLabels(new String[] {fromDate.toString(),toDate.toString()});
       	ArrayList<String> dateList=new ArrayList<String>();
       	int[] monthMarker=new int[12];
        for(int i=0;i<360;i++)
        {
        	@SuppressWarnings("deprecation")
  			Date date=new Date(0,i/30+1, i%30+1);
          	monthMarker[i/30]=1;
        	Random random=new Random();
        	double openPrice= random.nextDouble();
        	double closePrice= random.nextDouble();
        	double highPrice= random.nextDouble();
        	double lowPrice= random.nextDouble();
        	double volume= random.nextDouble();        			
        	Stock stock=new Stock(date,openPrice,closePrice,highPrice,lowPrice,volume);
        	series.appendData(new GraphViewData(i,closePrice), true);
        //    if(i%30==0)
        	dateList.add(date.toString());
        }

        String[] stockArr = new String[dateList.size()];
        stockArr = dateList.toArray(stockArr);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    //	graphView.setHorizontalLabels(stockArr);
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                   
                    return dateFormat.format(new Date(913,(int)(value/30)+1, (int)value%30+1));
                } else
                    return String.format("%.2f", value);
            }
        });
        graphView.getGraphViewStyle().setNumHorizontalLabels(4);
    	LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.ChartLayout);
    	layout.addView(graphView);
    	
        
        return rootView;
    }
}
