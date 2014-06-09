package com.marketstock.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.Portfolio_adapter;
import com.marketstock.adapter.listadapter;
import com.marketstock.adapter.portfolioListAdapter;


public class portFolio extends SherlockActivity{

	ListView portfolio_listview;
	ArrayList<HashMap<String, String>> stocklist = new ArrayList<HashMap<String, String>>();
	Portfolio_adapter portfolio_adaptor;
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
		portfolio_listview = (ListView)findViewById(R.id.portfolio_listview);
	
        HashMap<String, String> map;
        for(int i=0; i<10; i++)
        {
        	map = new HashMap<String, String>();
        	map.put(KEY_NAME, "Infosys");
        	map.put(KEY_HOLDING, i+1 + "");
        	map.put(KEY_AVG_PRICE,"85309");
        	map.put(KEY_AMOUNT, "856");
        	map.put(KEY_PROFIT, "5.08");
        	stocklist.add(map);
        
        }
        portfolio_adaptor = new Portfolio_adapter(context, stocklist);
		portfolio_listview.setAdapter(portfolio_adaptor);
	}

}
