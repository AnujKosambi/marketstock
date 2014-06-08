package com.marketstock.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.listadapter;

public class indices extends SherlockActivity{


	ListView listview;
	ArrayList<HashMap<String, String>> companylist = new ArrayList<HashMap<String, String>>();
	listadapter adapt;
	public static final String KEY_NAME = "name";
	public static final String KEY_DATE = "date";
	public static final String KEY_VALUE = "value";
	public static final String KEY_POINT_CHANGE = "point_change";
	public static final String KEY_PERCENT_CHANGE = "percent_change";

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context =this;
		setContentView(R.layout.indices_listview);
		listview = (ListView)findViewById(R.id.list_indices);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	
        HashMap<String, String> map;
        for(int i=0; i<10; i++)
        {
        	map = new HashMap<String, String>();
        	map.put(KEY_NAME, "Infosys");
        	map.put(KEY_DATE, (i+1)+",May,2014");
        	map.put(KEY_VALUE,"85309");
        	map.put(KEY_POINT_CHANGE, "856");
        	map.put(KEY_PERCENT_CHANGE, "5.08");
        	companylist.add(map);
        
        }
        adapt = new listadapter(context, companylist);
		listview.setAdapter(adapt);
        
    }
	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) 
	{
	
	
	    if ( item.getItemId() == android.R.id.home) {
	    	   MainActivity.actionBar.selectTab(MainActivity.actionBar.getTabAt(1));
	      NavUtils.navigateUpTo(this,new Intent(this,MainActivity.class));
	      
	        return  true;

	    } else {
	        return super.onOptionsItemSelected(item);
	    }
	}
	
}
