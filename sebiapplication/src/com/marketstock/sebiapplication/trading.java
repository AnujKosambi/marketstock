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
	SharedPreferences settings, prefs;
	TextView netvalue, netprofit;
	TextView careerNetWorth, careerLevel, careerWorth, sensexvi, sensexci;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.card, container, false);
		LinearLayout indicesCard = (LinearLayout) rootView
				.findViewById(R.id.indicesCard);
		LinearLayout moversCard = (LinearLayout) rootView
				.findViewById(R.id.moversCard);
		LinearLayout careerCard = (LinearLayout) rootView
				.findViewById(R.id.careerCard);
		LinearLayout portfolioCard = (LinearLayout) rootView
				.findViewById(R.id.portfolioCard);

		sensexvi = (TextView) rootView
				.findViewById(R.id.textSensexValueInt);
		sensexci = (TextView) rootView
				.findViewById(R.id.textSensexChangeInt);

		netvalue = (TextView) rootView.findViewById(R.id.textNetValueInt);
		netprofit = (TextView) rootView.findViewById(R.id.textNetProfitInt);

		careerWorth = (TextView) rootView
				.findViewById(R.id.careerWorthInt);
		careerLevel = (TextView) rootView
				.findViewById(R.id.careerLevelInt);
		careerNetWorth = (TextView) rootView
				.findViewById(R.id.careerNetWorthInt);

		prefs = rootView.getContext().getSharedPreferences(
				"com.marketstock.sebiapplication", Context.MODE_PRIVATE);

		settings = rootView.getContext()
				.getSharedPreferences(
						"com.marketstock.sebiapplication.setting",
						Context.MODE_PRIVATE);

		sensexvi.setText(prefs.getFloat("sensex", 0) + "");
		sensexci.setText(prefs.getFloat("sensexchange", 0) + "");

		careerWorth.setText(settings.getFloat("wallet", 0) + "");
		careerLevel.setText(settings.getInt("level", 0) + "");

		SQLiteDatabase d = MainActivity.db.getReadableDatabase();
		Cursor c = d
				.rawQuery(
						"select (select sum(profit) from userdata) totalprofit,(select sum(amount) from userdata) totalamount from userdata",
						null);

		if (c.moveToFirst()) {

			netvalue.setText(c.getString(c.getColumnIndex("totalamount")));
			netprofit.setText(c.getString(c.getColumnIndex("totalprofit")));
		}

		final Context context = getActivity().getApplicationContext();

		indicesCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				titleUpdate();
				Intent intent = new Intent(context, indices.class);
				startActivity(intent);

			}
		});

		moversCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				titleUpdate();
				Intent intent = new Intent(context, marketMovers.class);
				startActivity(intent);
			}
		});

		careerCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(context, News.class);
				// startActivity(intent);
				titleUpdate();
				Intent intent = new Intent(context, mycareer.class);
				startActivity(intent);

			}
		});

		portfolioCard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				titleUpdate();
				Intent intent = new Intent(context, portFolio.class);
				startActivity(intent);
			}
		});
		return rootView;
	}

	public void titleUpdate() {
		SQLiteDatabase d = MainActivity.db.getReadableDatabase();
		Cursor c = d
				.rawQuery(
						"select (select sum(profit) from userdata) totalprofit,(select sum(amount) from userdata) totalamount from userdata",
						null);

		if (c.moveToFirst() && c.getColumnIndex("totalamount") >= 0) {
			netvalue.setText(c.getString(c.getColumnIndex("totalamount")));

			if (portFolio.netProfit == 0)
				netprofit.setText(c.getString(c.getColumnIndex("totalprofit")));
			else
				netprofit.setText(portFolio.netProfit + "");
		}

		sensexvi.setText(prefs.getFloat("sensex", 0) + "");
		sensexci.setText(prefs.getFloat("sensexchange", 0) + "");
		careerWorth.setText(settings.getFloat("wallet", 0) + "");
		careerNetWorth.setText(settings.getFloat("networth", 0) + "");
		careerLevel.setText(settings.getInt("level", 0) + "");

	}
}