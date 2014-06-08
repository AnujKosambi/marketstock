package com.marketstock.sebiapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

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

	private Button getQuoteBtn;
	// Tab titles
	private String[] tabs = { "Learning Center", "Trade now" };

	static DBHelper db;
	private LinearLayout getMarqueeView()
	{
		LinearLayout linearLayout = new LinearLayout(this);

		String[] companies=Companies.getCompanies();
		for(int i=0;i<companies.length;i++)
		{
			TextView textView = new TextView(this);
			textView.setText(companies[i]+" "+100.0);
			textView.setTextColor(Color.RED);
			linearLayout.addView(textView);
			textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			
		}
		
		return linearLayout;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db = new DBHelper(this);
		
		Cursor s = db.getReadableDatabase().rawQuery("SELECT * FROM infosys",
				null);

		s.moveToFirst();
	     MarqueeLayout marqueeLayout = new MarqueeLayout(this);
	     marqueeLayout.setDuration(10000);
	     marqueeLayout.addView(getMarqueeView());
	     marqueeLayout.startAnimation();
	     LinearLayout marqueeParentLayout=(LinearLayout)findViewById(R.id.marquee_layout);
	     marqueeParentLayout.addView(marqueeLayout);
//		Log.d(s.getDouble(5)+"","ewf");
	     
		
		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
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

	
		s.close();
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
		 	if(item.getItemId()==R.id.action_getquote)
		 	{startActivity(new Intent(getApplicationContext(),
					Searchstock.class));
		 		
		 	}
			return super.onOptionsItemSelected(item);
	    
	    }
}
