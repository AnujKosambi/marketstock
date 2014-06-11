package com.marketstock.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.marketstock.adapter.StockPagesAdapter;
import com.marketstock.helper.Companies;
import com.marketstock.sebiapplication.models.Stock;

public class Stockpage extends SherlockFragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private StockPagesAdapter mAdapter;
	private ActionBar actionBar;
	public static Stock stock;
	public static double prevCloseprice;
	public static String companyName;
	public static double companyPrice;
	public static double companyLow52;
	public static double companyHigh52;

	// Tab titles
	private String[] tabs = { "Stock details", "Chart", "News" };
	public static ArrayList<com.marketstock.sebiapplication.models.News> news = new ArrayList<com.marketstock.sebiapplication.models.News>();
	public static HashMap<Integer, Boolean> dayList = new HashMap<Integer, Boolean>();

	public void updateNews(String companyName) {

		Cursor cursor;
		if (companyName.equals("infosys") || companyName.equals("tcs")
				|| companyName.equals("bajaj")) {
			cursor = MainActivity.db.getReadableDatabase().rawQuery(
					"SELECT * FROM " + companyName + "news where day <="
							+ (MainActivity.moveToDays - 54), null);

			cursor.moveToFirst();
			while (cursor.isAfterLast() == false) {
				int day = cursor.getInt(cursor.getColumnIndex("day"));
				com.marketstock.sebiapplication.models.News newsObj = new com.marketstock.sebiapplication.models.News(
						cursor.getString(cursor.getColumnIndex("title")),
						cursor.getString(cursor.getColumnIndex("desc")).replaceAll("//", "'"),
						cursor.getString(cursor.getColumnIndex("learning")).replaceAll("//", "'"),
						cursor.getString(cursor.getColumnIndex("effect")),
						cursor.getString(cursor.getColumnIndex("fluctuation")),
						day);
				if (dayList.containsKey(day))
					dayList.remove(day);
				dayList.put(day, true);

				news.add(newsObj);
				cursor.moveToNext();
			}
		} else {
			cursor = MainActivity.db.getReadableDatabase().rawQuery(
					"SELECT * FROM commonnews where company='" + companyName
							+ "' and day <=" + (MainActivity.moveToDays - 54),
					null);

			cursor.moveToFirst();
			while (cursor.isAfterLast() == false) {
				int day = cursor.getInt(cursor.getColumnIndex("day"));
				com.marketstock.sebiapplication.models.News newsObj = new com.marketstock.sebiapplication.models.News(
						cursor.getString(cursor.getColumnIndex("title")),
						cursor.getString(cursor.getColumnIndex("desc")),
						cursor.getString(cursor.getColumnIndex("learning")),
						"", "", day);
				if (dayList.containsKey(day))
					dayList.remove(day);
				dayList.put(day, true);
				news.add(newsObj);
				cursor.moveToNext();

				cursor.moveToNext();
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stockpage);

		viewPager = (ViewPager) findViewById(R.id.stockpager);
		viewPager.setScrollContainer(true);
		companyName = getIntent().getExtras().getString("Company") + "";

		updateNews(companyName);
		Companies.updateData(companyName);
		Companies.updateData52(companyName);
		companyPrice = Companies.PriceList.get(companyName);
		stock = Companies.StockList.get(companyName);
		prevCloseprice = Companies.prevPriceList.get(companyName);
		companyLow52 = Companies.low52.get(companyName);
		companyHigh52 = Companies.high52.get(companyName);

		actionBar = getSupportActionBar();
		mAdapter = new StockPagesAdapter(getSupportFragmentManager());
		// getQuoteBtn = (Button) findViewById(R.id.getquote_btn);

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
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getSupportMenuInflater();
	    //inflater.inflate(R.menu., menu);
	    return super.onCreateOptionsMenu(menu);
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
