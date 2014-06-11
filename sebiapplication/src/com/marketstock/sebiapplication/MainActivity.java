package com.marketstock.sebiapplication;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.marketstock.adapter.TabsPagerAdapter;
import com.marketstock.helper.Companies;
import com.marketstock.helper.MarqueeLayout;
import com.marketstock.sebiapplication.dbhelper.DBHelper;

public class MainActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	public static ActionBar actionBar;

	// Tab titles
	private String[] tabs = { "Learning Center", "Trade now" };

	public static DBHelper db;
	int width;
	public static long installed;
	public static int moveToDays;

	private void updateNewsEffect() {

		for (int i = 0; i < DBHelper.TB_NEWS.length; i++) {
			Cursor cursor = db.getReadableDatabase().rawQuery(
					"SELECT day,effect,fluctuation FROM " + DBHelper.TB_NEWS[i]
							+ " where day =" + (MainActivity.moveToDays - 54),
					null);
			String cname = DBHelper.TB_NEWS[i].substring(0,
					DBHelper.TB_NEWS[i].length() - 4);
			cursor.moveToFirst();
			ArrayList<com.marketstock.sebiapplication.models.News> newsList = new ArrayList<com.marketstock.sebiapplication.models.News>();
			while (cursor.isAfterLast() == false) {
				int day = cursor.getInt(cursor.getColumnIndex("day"));
				String effect = cursor.getString(cursor
						.getColumnIndex("effect"));
				Double fluctuation = cursor.getDouble(cursor
						.getColumnIndex("fluctuation"));
				com.marketstock.sebiapplication.models.News news = new com.marketstock.sebiapplication.models.News(
						"", "", "", effect, fluctuation + "", day);
				newsList.add(news);
				cursor.moveToNext();

			}
			cursor.close();
			for (com.marketstock.sebiapplication.models.News news : newsList) {
				Cursor ucursor = db.getReadableDatabase()
						.rawQuery(
								"SELECT id,closePrice FROM " + cname
										+ " order by date", null);
				int id = -1;
				ucursor.moveToPosition(news.getDay() + 54);
				id = ucursor.getInt(ucursor.getColumnIndex("id"));

				// Toast.makeText(this,id+" "+DBHelper.TB_NEWS[i],
				// Toast.LENGTH_SHORT).show();
				ContentValues cv = new ContentValues();
				ucursor.moveToPrevious();
				Double preValue = ucursor.getDouble(ucursor
						.getColumnIndex("closePrice"));
				double change = 0.01
						* Double.parseDouble(news.getFlucatuation()) * preValue;
				if (news.getEffect().equals("Up")) {
					cv.put("closePrice", preValue + change);

					// Toast.makeText(this,id+" "+(preValue+change),
					// Toast.LENGTH_LONG).show();
				} else {
					cv.put("closePrice", preValue - change);
					// Toast.makeText(this,id+" "+(preValue-change),
					// Toast.LENGTH_SHORT).show();
				}
				ucursor.close();
				db.getWritableDatabase().update(cname, cv, "id=" + id, null);

			}
		}

	}

	public static void updateMarqueeView() {
		String[] companies = Companies.getCompanies();
		for (int i = 0; i < companies.length; i++) {

			Companies.updateData(companies[i]);

			double change = Companies.PriceList.get(companies[i])
					- Companies.prevPriceList.get(companies[i]);
			marqueeView.get(companies[i]).setText(
					companies[i].toUpperCase()
							+ " "
							+ String.format("%.2f",
									Companies.PriceList.get(companies[i]))
							+ " (" + String.format("%.2f", change) + ")");
			// Log.d("Marquee", companies[i].toUpperCase()+" "+
			// String.format("%.2f",Companies.PriceList.get(companies[i]))+
			// " ("+
			// String.format("%.2f",change)
			// +")     ");
		}
	}

	public static HashMap<String, TextView> marqueeView = new HashMap<String, TextView>();

	@Override
	public void onResume() {

		super.onResume();
		updateMarqueeView();

	}

	@Override
	public void onStart() {
		super.onStart();
		updateMarqueeView();
	}

	private View getMarqueeView() {
		// HorizontalScrollView scrollView=new HorizontalScrollView(this);
		LinearLayout linearLayout = new LinearLayout(this);

		String[] companies = Companies.getCompanies();

		for (int i = 0; i < companies.length; i++) {
			Companies.updateData(companies[i]);
			TextView textView = new TextView(this);
			marqueeView.put(companies[i], textView);
			double change = Companies.PriceList.get(companies[i])
					- Companies.prevPriceList.get(companies[i]);
			textView.setText(companies[i].toUpperCase()
					+ " "
					+ String.format("%.2f",
							Companies.PriceList.get(companies[i])) + " ("
					+ String.format("%.2f", change) + ")     ");
			if (change < 0)
				textView.setTextColor(Color.rgb(252, 82, 82));
			else
				textView.setTextColor(Color.rgb(82, 252, 82));

			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextSize(20);
			width += textView.getText().length() * 20;
			linearLayout.addView(textView);
			// textView.setLayoutParams(new
			// LayoutParams(LayoutParams.MATCH_PARENT,
			// LayoutParams.MATCH_PARENT));
			linearLayout.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			// scrollView.addView(linearLayout, 2000,
			// LayoutParams.WRAP_CONTENT);

		}

		return linearLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			db = new DBHelper(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Cursor c = db.getReadableDatabase().rawQuery("select * from axis",
		// null);

		SharedPreferences prefs = getApplicationContext().getSharedPreferences(
				"com.marketstock.sebiapplication", Context.MODE_PRIVATE);

		String LastUsed = prefs.getString("LastUsed", "");

		installed = prefs.getLong("Date", 0);
		String newUsed = Calendar.getInstance().get(Calendar.DATE) + "-"
				+ Calendar.getInstance().get(Calendar.MONTH) + "-"
				+ Calendar.getInstance().get(Calendar.YEAR);
		prefs.edit().putString("LastUsed", newUsed).commit();

		Intent intent = new Intent(this, priceService.class);
		startService(intent);

		Date dateNow = new Date(Calendar.getInstance().getTimeInMillis());
		moveToDays = (int) ((dateNow.getTime() - installed) / (1000 * 60 * 60 * 24));
		moveToDays += 55;
		if (!newUsed.equals(LastUsed)) {
			;
			updateNewsEffect();
		}
		MarqueeLayout marqueeLayout = new MarqueeLayout(this);
		marqueeLayout.setBackgroundColor(Color.BLACK);
		marqueeLayout.setDuration(100000);
		marqueeLayout.addView(getMarqueeView());
		marqueeLayout.startAnimation();
		marqueeLayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		LinearLayout marqueeParentLayout = (LinearLayout) findViewById(R.id.marquee_layout);
		marqueeParentLayout.addView(marqueeLayout, width,
				LayoutParams.WRAP_CONTENT);

		marqueeParentLayout.setBackgroundColor(Color.BLACK);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
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
		 * 
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
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_getquote) {
			startActivity(new Intent(getApplicationContext(), Searchstock.class));

		}
		return super.onOptionsItemSelected(item);

	}
}
