package com.marketstock.sebiapplication;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.marketstock.helper.Companies;

public class portFolio extends SherlockActivity {

	ListView portfolio_listview;
	ArrayList<HashMap<String, String>> stocklist = new ArrayList<HashMap<String, String>>();
	Portfolio_adapter portfolio_adaptor;
	public static final String KEY_NAME = "scrip"; // Company Name
	public static final String KEY_HOLDING = "holding"; // No of stocks
	public static final String KEY_AVG_PRICE = "avg. price";
	public static final String KEY_AMOUNT = "amount";
	public static final String KEY_PROFIT = "profit";
	public static double netProfit = 0;

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

		netProfit = 0.0;
		if (c.moveToFirst())
			do {
				map = new HashMap<String, String>();
				map.put(KEY_NAME, c.getString(c.getColumnIndex("company")));
				map.put(KEY_HOLDING, c.getString(c.getColumnIndex("holdings")));
				map.put(KEY_AVG_PRICE,
						c.getString(c.getColumnIndex("avg_price")));
				double amount = Math.round(Double.parseDouble(c.getString(c
						.getColumnIndex("amount"))) * 100.0) / 100.0;
				map.put(KEY_AMOUNT, amount + "");

				double aprice = Double.parseDouble(c.getString(c
						.getColumnIndex("avg_price")));
				int holding = Integer.parseInt(c.getString(c
						.getColumnIndex("holdings")));
				Companies.updateData(c.getString(c.getColumnIndex("company")));
				double price = Companies.PriceList.get(c.getString(c
						.getColumnIndex("company")));
				double profit = (price - aprice) * holding;
				profit = Math.round(profit * 100.0) / 100.0;
				netProfit += profit;
				map.put(KEY_PROFIT, profit + "");
				stocklist.add(map);
			} while (c.moveToNext());
		else {
			/*final Dialog dialog = new Dialog(portFolio.this);
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
			dialog.show();*/
			AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
			alertBuilder.setTitle("No trading yet");
			alertBuilder.setMessage("Buy stock to get started.!");
			alertBuilder.setIcon(android.R.drawable.stat_sys_warning);
			alertBuilder.setCancelable(true);
			alertBuilder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			alertBuilder.create();
			alertBuilder.show();
		}

		portfolio_adaptor = new Portfolio_adapter(context, stocklist);
		portfolio_listview.setAdapter(portfolio_adaptor);
	}

}
