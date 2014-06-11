package com.marketstock.sebiapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.BaseInflaterAdapter;
import com.marketstock.adapter.CardItemData;
import com.marketstock.adapter.inflaters.CardInflater;

public class TipOfDay extends SherlockActivity {
	ListView list;
	Context context = getApplication();
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Context context = getApplication();
			setContentView(R.layout.tipofday);
			ActionBar actionBar=getSupportActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			Log.e("in inflate", "inflate");
		 	list = (ListView) findViewById(R.id.tip_list_view);
			list.addHeaderView(new View(context));
			list.addFooterView(new View(context));
			
			SharedPreferences prefer =getApplicationContext().getSharedPreferences(
				      "com.marketstock.sebiapplication", Context.MODE_PRIVATE);
			int index = prefer.getInt("index", 0);
			int temp=0;
			BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
			Cursor cursor = MainActivity.db.getReadableDatabase()
					.rawQuery("SELECT * FROM "+ "tips",null);
			if(index>=15)
			{
				cursor.moveToFirst();
				while(cursor.isAfterLast()==false)
				{
					CardItemData data = new CardItemData(cursor.getString(1));
					adapter.addItem(data, false);
					Log.e("notif", data.toString());
					cursor.moveToNext();
				}
			}
			else
			{
				cursor.moveToFirst();
				while(temp<=index)
				{
					CardItemData data = new CardItemData(cursor.getString(1));
					adapter.addItem(data, false);
					Log.e("notif", data.toString());
					cursor.moveToNext();
					temp++;
				}
			}
			list.setAdapter(adapter);
			cursor.close();

	 }
	 
	

}
