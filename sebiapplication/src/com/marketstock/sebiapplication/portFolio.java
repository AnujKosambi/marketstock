package com.marketstock.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.marketstock.adapter.portfolioListAdapter;


public class portFolio extends SherlockFragment{

	ListView portfolio_listview;
	ArrayList<HashMap<String, String>> stocklist = new ArrayList<HashMap<String, String>>();
	portfolioListAdapter portfolio_adaptor;
	public static final String KEY_NAME = "scrip"; //Company Name
	public static final String KEY_HOLDING = "holding"; //No of stocks
	public static final String KEY_AVG_PRICE = "avg. price";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_PROFIT = "profit";

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.portfolio, container, false);
        
        
        return rootView;
    }
}
