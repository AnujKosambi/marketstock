package com.marketstock.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class portfolioListAdapter extends BaseAdapter{
	 private Context activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	 
	    public portfolioListAdapter(Context a, ArrayList<HashMap<String, String>> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    @Override
		public int getCount() {
	        return data.size();
	    }
	 
	    @Override
		public Object getItem(int position) {
	        return position;
	    }
	 
	    @Override
		public long getItemId(int position) {
	        return position;
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        
        return convertView;
        
    }
	
}
