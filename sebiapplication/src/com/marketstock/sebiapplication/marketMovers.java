package com.marketstock.sebiapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class marketMovers extends SherlockActivity{

	TextView gainer_company1_name;
	TextView gainer_company1_value;
	TextView gainer_company1_percent;
	TextView gainer_company2_name;
	TextView gainer_company2_value;
	TextView gainer_company2_percent;
	
	TextView loser_company1_name;
	TextView loser_company1_value;
	TextView loser_company1_percent;
	TextView loser_company2_name;
	TextView loser_company2_value;
	TextView loser_company2_percent;
	FrameLayout gainers;
	FrameLayout losers;
	TextView top_gainers;
	TextView top_losers;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.market_movers);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		gainers = (FrameLayout) findViewById(R.id.gainers);
		losers = (FrameLayout) findViewById(R.id.losers);
		top_gainers = (TextView) findViewById(R.id.topgainers);
		top_losers = (TextView) findViewById(R.id.toplosers);
		
		gainer_company1_name = (TextView) findViewById(R.id.gainers_company1_name);
		gainer_company1_value = (TextView) findViewById(R.id.gainers_company1_value);
		gainer_company1_percent = (TextView) findViewById(R.id.gainers_company1_percent);
		gainer_company2_name = (TextView) findViewById(R.id.gainers_company2_name);
		gainer_company2_value = (TextView) findViewById(R.id.gainers_company2_value);
		gainer_company2_percent = (TextView) findViewById(R.id.gainers_company2_percent);
		
		loser_company1_name = (TextView) findViewById(R.id.losers_company1_name);
		loser_company1_value = (TextView) findViewById(R.id.losers_company1_value);
		loser_company1_percent = (TextView) findViewById(R.id.losers_company1_percent);
		loser_company2_name = (TextView) findViewById(R.id.losers_company2_name);
		loser_company2_value = (TextView) findViewById(R.id.losers_company2_value);
		loser_company2_percent = (TextView) findViewById(R.id.losers_company2_percent);
		
		Cursor cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM companydata ORDER BY percentChange DESC",null);
		cursor.moveToFirst();
		
		if(cursor.getDouble(3)>0)
		{
			gainer_company1_name.setText(cursor.getString(0).toUpperCase());
			gainer_company1_value.setText((Math.round((cursor.getDouble(1))*100))/100+"");
			gainer_company1_percent.setText((Math.round((cursor.getDouble(3))*100))/100+"%");
			
			cursor.moveToNext();
			
			gainer_company2_name.setText(cursor.getString(0).toUpperCase());
			gainer_company2_value.setText((Math.round((cursor.getDouble(1))*100))/100+"");
			gainer_company2_percent.setText((Math.round((cursor.getDouble(3))*100))/100+"%");
		}
		else
		{
			gainers.setVisibility(View.GONE);
			top_gainers.setText("No Gainer");
		}
		
		cursor.moveToLast();
		
		if(cursor.getDouble(3)<=0)
		{
			loser_company1_name.setText(cursor.getString(0).toUpperCase());
			loser_company1_value.setText((Math.round((cursor.getDouble(1))*100))/100+"");
			loser_company1_percent.setText((Math.round((cursor.getDouble(3))*100))/100+"%");
			
			cursor.moveToPrevious();
			if(cursor.getDouble(3)<=0)
			{
				loser_company2_name.setText(cursor.getString(0).toUpperCase());
				loser_company2_value.setText((Math.round((cursor.getDouble(1))*100))/100+"");
				loser_company2_percent.setText((Math.round((cursor.getDouble(3))*100))/100+"%");
			}
		}
		else
		{
			top_losers.setText("No Losers");
			losers.setVisibility(View.GONE);
		}
		cursor.close();
	}
}
