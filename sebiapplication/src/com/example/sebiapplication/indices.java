package com.example.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.exapmle.adapter.listadapter;

public class indices extends SherlockFragment{

	ActionBar bar;
	ListView listview;
	ArrayList<HashMap<String, String>> companylist = new ArrayList<HashMap<String, String>>();
	listadapter adapt;
	public static final String KEY_NAME = "name";
	public static final String KEY_DATE = "date";
	public static final String KEY_VALUE = "value";
	public static final String KEY_POINT_CHANGE = "point_change";
	public static final String KEY_PERCENT_CHANGE = "percent_change";
	View rootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		Context context = getActivity();
        View rootView = inflater.inflate(R.layout.indices_listview, container, false);
        bar = getSherlockActivity().getSupportActionBar();
        listview = (ListView) rootView.findViewById(R.id.list_indices);
        getSherlockActivity().getSupportActionBar().setTitle("App Name");
        HashMap<String, String> map;
        for(int i=0; i<10; i++)
        {
        	map = new HashMap<String, String>();
        	map.put(KEY_NAME, "Infosys");
        	map.put(KEY_DATE, (i+1)+",May,2014");
        	map.put(KEY_VALUE,"85309");
        	map.put(KEY_POINT_CHANGE, "856");
        	map.put(KEY_PERCENT_CHANGE, "5.08");
        	companylist.add(map);
        	Log.e("Map", "added "+i);
        }
        adapt = new listadapter(context, companylist);
		listview.setAdapter(adapt);
        return rootView;
    }
}
