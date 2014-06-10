package com.marketstock.sebiapplication;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.BaseInflaterAdapter;
import com.marketstock.adapter.CardItemData;
import com.marketstock.adapter.CardItemTitleNData;
import com.marketstock.adapter.inflaters.CardInflater;
import com.marketstock.adapter.inflaters.CardInflaterQuote;
import com.marketstock.helper.Companies;
import com.marketstock.sebiapplication.dbhelper.DBHelper;

public class News extends SherlockActivity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context =this;
		setContentView(R.layout.cards_listview);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		ListView list = (ListView) findViewById(R.id.list_view);

		list.addHeaderView(new View(context));
		list.addFooterView(new View(context));
		
		BaseInflaterAdapter<CardItemTitleNData> adapter = new BaseInflaterAdapter<CardItemTitleNData>(new CardInflaterQuote());
		Cursor cursor;
		String[] companies=DBHelper.TB_NEWS;
		for(int i=0;i<companies.length;i++){
				cursor= MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM "+companies[i] +" where day <="+(MainActivity.moveToDays-54),null); 
			cursor.moveToFirst();
			while(cursor.isAfterLast()==false)
			{
			CardItemTitleNData data = new CardItemTitleNData(
					cursor.getString(cursor.getColumnIndex("title")),
					cursor.getString(cursor.getColumnIndex("desc")),
					cursor.getString(cursor.getColumnIndex("learning")));
			adapter.addItem(data, false);
			cursor.moveToNext();
			}
			cursor.close();	
		}
		cursor= MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM commonnews where day <="+(MainActivity.moveToDays-54),null);
		cursor.moveToFirst();
		while(cursor.isAfterLast()==false)
		{
		CardItemTitleNData data = new CardItemTitleNData(
				cursor.getString(cursor.getColumnIndex("title")),
				cursor.getString(cursor.getColumnIndex("desc")),
				cursor.getString(cursor.getColumnIndex("learning")));
		adapter.addItem(data, false);
		cursor.moveToNext();
		}
		cursor.close();	
		list.setAdapter(adapter);



	}
}
