package com.marketstock.sebiapplication;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.marketstock.adapter.portfolioListAdapter;


public class portFolio extends SherlockActivity{

	ListView portfolio_listview;
	ArrayList<HashMap<String, String>> stocklist = new ArrayList<HashMap<String, String>>();
	portfolioListAdapter portfolio_adaptor;
	public static final String KEY_NAME = "scrip"; //Company Name
	public static final String KEY_HOLDING = "holding"; //No of stocks
	public static final String KEY_AVG_PRICE = "avg. price";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_PROFIT = "profit";

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context =this;
		setContentView(R.layout.portfolio);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

}
