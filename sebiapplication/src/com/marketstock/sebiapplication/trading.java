package com.marketstock.sebiapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class trading extends SherlockFragment {

	Context context = getActivity();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.card, container, false);
		LinearLayout indicesCard = (LinearLayout) rootView
				.findViewById(R.id.indicesCard);
		LinearLayout moversCard = (LinearLayout) rootView
				.findViewById(R.id.moversCard);
		LinearLayout newsCard = (LinearLayout) rootView
				.findViewById(R.id.newsCard);
		LinearLayout portfolioCard = (LinearLayout) rootView
				.findViewById(R.id.portfolioCard);
		
		TextView sensexvi = (TextView) rootView.findViewById(R.id.textSensexValueInt);
		TextView sensexci = (TextView) rootView.findViewById(R.id.textSensexChangeInt);
		
		SharedPreferences prefs = rootView.getContext()
				.getSharedPreferences(
						"com.marketstock.sebiapplication",
						Context.MODE_PRIVATE);
		
		sensexvi.setText(prefs.getFloat("sensex", 0)+"");
		sensexci.setText(prefs.getFloat("sensexchange", 0)+"");
		

		SQLiteDatabase d = MainActivity.db.getReadableDatabase();
		Cursor c = d
				.rawQuery(
						"select (select sum(profit) from userdata) totalprofit,(select sum(amount) from userdata) totalamount from userdata",
						null);

		if (c.moveToFirst()) {

			TextView netvalue = (TextView) rootView
					.findViewById(R.id.textNetValueInt);
			TextView netprofit = (TextView) rootView
					.findViewById(R.id.textNetProfitInt);

			netvalue.setText(c.getString(c.getColumnIndex("totalamount")));
			netprofit.setText(c.getString(c.getColumnIndex("totalprofit")));
		}

		final Context context = getActivity().getApplicationContext();
		indicesCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, indices.class);
				startActivity(intent);
			}
		});

		moversCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, marketMovers.class);
				startActivity(intent);
			}
		});

		newsCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, News.class);
				startActivity(intent);
			}
		});

		portfolioCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, portFolio.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
}