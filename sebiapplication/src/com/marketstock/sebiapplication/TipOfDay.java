package com.marketstock.sebiapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.BaseInflaterAdapter;
import com.marketstock.adapter.CardItemData;
import com.marketstock.adapter.inflaters.CardInflater;

public class TipOfDay extends SherlockActivity {
	
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Context context =this;
			setContentView(R.layout.tipofday);
			ActionBar actionBar=getSupportActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			ListView list = (ListView) findViewById(R.id.tip_list_view);

			list.addHeaderView(new View(context));
			list.addFooterView(new View(context));
			
			BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
			for (int i = 0; i < 16; i++)
			{
				CardItemData data = new CardItemData("Item " + i);
				adapter.addItem(data, false);
			}

			list.setAdapter(adapter);
			
	 }
	 
	 

}
