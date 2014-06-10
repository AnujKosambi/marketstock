package com.marketstock.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.Portfolio_adapter;

public class portFolio extends SherlockActivity {

	ListView portfolio_listview;
	ArrayList<HashMap<String, String>> stocklist = new ArrayList<HashMap<String, String>>();
	Portfolio_adapter portfolio_adaptor;
	public static final String KEY_NAME = "scrip"; // Company Name
	public static final String KEY_HOLDING = "holding"; // No of stocks
	public static final String KEY_AVG_PRICE = "avg. price";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_PROFIT = "profit";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context = this;
		setContentView(R.layout.portfolio);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		portfolio_listview = (ListView) findViewById(R.id.portfolio_listview);

		SQLiteDatabase d = MainActivity.db.getReadableDatabase();
		Cursor c = d.rawQuery("select * from userdata", null);
		HashMap<String, String> map;
		if (c.moveToFirst())
			do {
				map = new HashMap<String, String>();
				map.put(KEY_NAME, c.getString(c.getColumnIndex("company")));
				map.put(KEY_HOLDING, c.getString(c.getColumnIndex("holdings")));
				map.put(KEY_AVG_PRICE,
						c.getString(c.getColumnIndex("avg_price")));
				map.put(KEY_AMOUNT, c.getString(c.getColumnIndex("amount")));
				map.put(KEY_PROFIT, c.getString(c.getColumnIndex("profit")));
				stocklist.add(map);
			} while (c.moveToNext());
		else {
			final Dialog dialog = new Dialog(portFolio.this);
			dialog.setTitle("Error");
			LinearLayout linearLayout = new LinearLayout(portFolio.this);
			linearLayout.setPadding(25, 25, 25, 25);
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			TextView desc = new TextView(getApplicationContext());
			desc.setText("You need to buy some stock.Search stock in Get Quotes and Buy them");
			Button ok = new Button(getApplicationContext());
			ok.setText("OK");
			ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			linearLayout.addView(desc, LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			linearLayout.addView(ok);
			dialog.setContentView(linearLayout);
			dialog.show();
		}

		portfolio_adaptor = new Portfolio_adapter(context, stocklist);
		portfolio_listview.setAdapter(portfolio_adaptor);
	}

}
