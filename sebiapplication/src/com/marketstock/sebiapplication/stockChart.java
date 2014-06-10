package com.marketstock.sebiapplication;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.storage.StorageManager;
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

public class stockChart extends SherlockFragment {
	private static SimpleDateFormat dateformat = new SimpleDateFormat(
			"dd/MM/yyyy");
	public static java.util.Date startdate;
	public static java.util.Date enddate;

	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			startdate = dateformat.parse("01/01/2011");
			enddate = dateformat.parse("31/12/2012");
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
   
       	graphView.getGraphViewStyle().setVerticalLabelsWidth(80);
      
       	graphView.getGraphViewStyle().setGridColor(Color.GRAY);
       	graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
       	graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
       	graphView.getGraphViewStyle().setTextSize(12);
     	graphView.getGraphViewStyle().setNumHorizontalLabels(5);
       //	graphView.getGraphViewStyle().setNumVerticalLabels(4);
       //	graphView.getGraphViewStyle().setVerticalLabelsWidth(20);
       //	graphView.getGraphViewStyle().
       	graphView.setDrawBackground(true);
       	//graphView.
       	
       	
       	graphView.setBackgroundColor(Color.argb(150,194, 223, 255));
        graphView.setDrawDataPoints(true);
       	graphView.setDataPointsRadius(4f);
       	
       	graphView.setShowLegend(true);
    
       	graphView.setLegendAlign(LegendAlign.MIDDLE);
       	graphView.setLegendWidth(100);
        final HashMap<Integer,Date> dateMarker=new HashMap<Integer,Date>();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
     	ArrayList<GraphViewData> graphData=new ArrayList<GraphViewData>();
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
         
                	  return ((Date)dateMarker.get((int)value)).toString();
                } else
                    return String.format("%.2f", value);
            }
        });
 
      
       	Cursor cursor = MainActivity.db.getReadableDatabase().rawQuery("SELECT id,date,closePrice FROM "+Stockpage.companyName+" order by date ",null);
       	cursor.moveToFirst();    
        int i=0;
      
        while (cursor.isAfterLast() == false && i<MainActivity.moveToDays) 
        { 
        
			Date date;

			date = new Date(Long.parseLong(cursor.getString(1)));
			dateMarker.put(i, date);
        	double closePrice= cursor.getDouble(2);
       // 	Stock stock=new Stock(new Date(i++),openPrice,closePrice,highPrice,lowPrice,volume);
        	
        	graphData.add(new GraphViewData( i++,closePrice));
        	cursor.moveToNext();
        	
        }
        Toast.makeText(rootView.getContext(),i+" ", Toast.LENGTH_LONG).show();
        if(i<=0)
        	return rootView;
        
        GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
        GraphViewData[] graphDataArray= new GraphViewData[graphData.size()];
        
        graphDataArray=graphData.toArray(graphDataArray);
        GraphViewSeries series = new GraphViewSeries(Stockpage.companyName, seriesStyle,graphDataArray);
        graphView.addSeries(series); // data
      
        graphView.getGraphViewStyle().setNumHorizontalLabels(5);
     //   graphView.setViewPort(240,10);
    	LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.ChartLayout);
    	//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	
    	layout.addView(graphView);
    	SeekBar seekBar=(SeekBar)rootView.findViewById(R.id.ChartSeekBar);
    	final SeekBar seekBarSize=(SeekBar)rootView.findViewById(R.id.ChartSeekBarSize);
    	final TextView startText=(TextView)rootView.findViewById(R.id.ChartStartSeekValue);
    	final TextView sizeText=(TextView)rootView.findViewById(R.id.ChartSizeSeekValue);
    	final ValueOfInt startInt=new ValueOfInt(0);
    	
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

				int startPos=((progress*(dateMarker.size()-1)*(100-seekBarSize.getProgress()))/10000);
				graphView.setViewPort(startPos,seekBarSize.getProgress());
				startInt._int=startPos;
				
				startText.setText(""+ (Date)dateMarker.get(startPos));

				graphView.redrawAll();
				// graphView.invalidate();

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
				int startPos=	startInt._int;
				graphView.setViewPort(startPos,(( progress*(dateMarker.size()-1-startPos))/100));
				sizeText.setText(""+progress+"%");

				graphView.redrawAll();
			}
		});
		return rootView;
	}
}
class ValueOfInt{
	int _int;
	ValueOfInt(int i) {
		_int=i;
	}
	
}
