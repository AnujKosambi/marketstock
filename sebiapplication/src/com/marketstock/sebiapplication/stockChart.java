package com.marketstock.sebiapplication;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.jar.JarEntry;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.marketstock.helper.GraphViewData;
import com.marketstock.sebiapplication.models.Stock;

public class stockChart extends SherlockFragment {
	private static SimpleDateFormat dateformat=new SimpleDateFormat("dd/MM/yyyy");
	public static  java.util.Date startdate ;
	public static java.util.Date enddate ;

	@SuppressLint("SimpleDateFormat") @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		try {
			startdate=dateformat.parse("01/01/2011");
			enddate=dateformat.parse("31/12/2012");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        final View rootView = inflater.inflate(R.layout.stock_chart, container, false);
       // GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
        
	 //   GraphViewSeries series = new GraphViewSeries("Infosys", seriesStyle, new GraphViewData[] {});
       	final LineGraphView graphView = new LineGraphView(this.getActivity(), "Quote");
       	
      // 	graphView.setScrollable(true);
      	graphView.setScalable(true);
        graphView.setGravity(Gravity.BOTTOM);
   
       	graphView.getGraphViewStyle().setVerticalLabelsWidth(30);
      
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
       	graphView.setDataPointsRadius(4f);
       	
       	graphView.setShowLegend(true);
    
       	graphView.setLegendAlign(LegendAlign.MIDDLE);
       	graphView.setLegendWidth(100);

       	Cursor cursor = MainActivity.db.getReadableDatabase().rawQuery("SELECT id,date,closePrice FROM infosys ",null);
       	cursor.moveToFirst();
       	ArrayList<GraphViewData> graphData=new ArrayList<GraphViewData>();
      final HashMap<Date,Integer> dateMarker=new HashMap<Date,Integer>();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
         //       Log.d("Date", indexToDate.get((int)value).toString());
        //            return dateFormat.format(indexToDate.get((int)value)).toString();
                	  return String.format("%.0f", value);// dateFormat.format(new Date((long)(value*(1000 * 60 * 60 * 24))+startdate.getTime())).toString();
                    
                } else
                    return String.format("%.2f", value);
            }
        });
 
      
        int i=0;
        while (cursor.isAfterLast() == false) 
        {
      
			Date date;
			try {
				SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
				date = new Date(format.parse(cursor.getString(1)).getTime());
			
			
        	Random random=new Random();
        	double openPrice= random.nextDouble();
        	double closePrice= cursor.getDouble(2);
        	double highPrice= random.nextDouble();
        	double lowPrice= random.nextDouble();
        	
        	double volume= random.nextDouble();        			
       // 	Stock stock=new Stock(new Date(i++),openPrice,closePrice,highPrice,lowPrice,volume);
        	
      
        	if(date.getTime()>=startdate.getTime() && date.getTime()<=enddate.getTime())
        	graphData.add(new GraphViewData( date,closePrice));
        	
        	
   
			} catch (ParseException e) {				
				e.printStackTrace();
			}
        
        	cursor.moveToNext();
        	
        }
        GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
        GraphViewData[] graphDataArray= new GraphViewData[graphData.size()];
        
        graphDataArray=graphData.toArray(graphDataArray);
        GraphViewSeries series = new GraphViewSeries("Infosys", seriesStyle,graphDataArray);
        graphView.addSeries(series); // data
      
        graphView.getGraphViewStyle().setNumHorizontalLabels(4);
     //   graphView.setViewPort(240,10);
    	LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.ChartLayout);
    	//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	
    	layout.addView(graphView);
    	SeekBar seekBar=(SeekBar)rootView.findViewById(R.id.ChartSeekBar);
    	final SeekBar seekBarSize=(SeekBar)rootView.findViewById(R.id.ChartSeekBarSize);
    	final TextView startText=(TextView)rootView.findViewById(R.id.ChartStartSeekValue);
    	final TextView sizeText=(TextView)rootView.findViewById(R.id.ChartSizeSeekValue);
    	
    	seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				graphView.setViewPort(progress, seekBarSize.getProgress());
				startText.setText(""+progress);
				graphView.redrawAll();
			//	graphView.invalidate();
				
			}
		});
    
    	seekBarSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				graphView.setViewPort(Integer.parseInt((String) startText.getText()), progress);
				sizeText.setText(""+progress);
				graphView.redrawAll();
			}
		});
        return rootView;
    }
}
