package com.marketstock.sebiapplication;

import java.sql.Date;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.marketstock.adapter.StockPagesAdapter;
import com.marketstock.sebiapplication.models.Stock;

public class Stockpage extends SherlockFragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private StockPagesAdapter mAdapter;
	private ActionBar actionBar; 
	public static Stock stock;
	public static double prevCloseprice;
	public static String companyName;
	public static double companyPrice;
	
	// Tab titles
	private String[] tabs = { "Stock details", "Chart","News" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_stockpage);
		setContentView(R.layout.activity_stockpage);
		
		
		viewPager = (ViewPager) findViewById(R.id.stockpager);
		viewPager.setScrollContainer(true);
		companyName=getIntent().getExtras().getString("Company")+"";
		Cursor cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT* FROM "+ companyName+" order by date",null); 
		
		
		int moveToDays=MainActivity.moveToDays;
		//Toast.makeText(this, moveToDays+"", Toast.LENGTH_LONG).show();
		cursor.moveToPosition(moveToDays);
		
		stock=new Stock(new Date(cursor.getLong(1)), 
			cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5),cursor.getDouble(6));
		cursor.moveToPrevious();
		prevCloseprice=cursor.getDouble(3);
		cursor.close();
		cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT* FROM companydata",null); 
		
		actionBar = getSupportActionBar();
		mAdapter = new StockPagesAdapter(getSupportFragmentManager());
		//getQuoteBtn = (Button) findViewById(R.id.getquote_btn);

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
