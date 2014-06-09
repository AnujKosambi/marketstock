package com.marketstock.sebiapplication;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.marketstock.adapter.BaseInflaterAdapter;
import com.marketstock.adapter.CardItemData;
import com.marketstock.adapter.CardItemTitleNData;
import com.marketstock.adapter.inflaters.CardInflater;
import com.marketstock.adapter.inflaters.CardInflaterQuote;

public class stockNews extends SherlockFragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		Context context = getActivity();
        View rootView = inflater.inflate(R.layout.cards_listview, container, false);
        ListView list = (ListView)rootView.findViewById(R.id.list_view);

		list.addHeaderView(new View(context));
		list.addFooterView(new View(context));
		
		BaseInflaterAdapter<CardItemTitleNData> adapter = new BaseInflaterAdapter<CardItemTitleNData>(new CardInflaterQuote());
		Cursor cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM "+ "infosysnew",null); 
		cursor.moveToFirst();
		while(cursor.isAfterLast()==false)
		{
			CardItemTitleNData data = new CardItemTitleNData(cursor.getString(1), cursor.getString(2));
			adapter.addItem(data, false);
			cursor.moveToNext();
		}

		list.setAdapter(adapter);
        
        return rootView;
    }
}
