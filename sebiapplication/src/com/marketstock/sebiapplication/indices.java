package com.marketstock.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.listadapter;
import com.marketstock.sebiapplication.dbhelper.DBHelper;

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
		
		TextView sensex = (TextView) findViewById(R.id.indices_sensex_change);
		
		float change=0.0f;
		
        HashMap<String, String> map;
        
        SQLiteDatabase d = MainActivity.db.getReadableDatabase();
         
        Cursor c = d.rawQuery("select * from "+DBHelper.TB_COMPANYDATA, null);
        if(c.moveToFirst())
        do{
        	map = new HashMap<String, String>();        	
        	map.put(KEY_NAME, c.getString(c.getColumnIndex("company")));
//        	map.put(KEY_DATE, MainActivity.moveToDays+"");
        	map.put(KEY_VALUE, c.getString(c.getColumnIndex("price")));
        	map.put(KEY_POINT_CHANGE, c.getString(c.getColumnIndex("weight")));
        	map.put(KEY_PERCENT_CHANGE, c.getString(c.getColumnIndex("percentChange")));
        	
        	change += Float.parseFloat(c.getString(c.getColumnIndex("weight")));
        	companylist.add(map);
        	
        }while(c.moveToNext());
        
        sensex.setText(change+"");
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
