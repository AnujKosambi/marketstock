package com.marketstock.sebiapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.marketstock.helper.Companies;
import com.marketstock.sebiapplication.dbhelper.DBHelper;

public class BuySell extends Activity {

	static Context cont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_sell);
		cont = getApplicationContext();
		final ToggleButton bst = (ToggleButton) findViewById(R.id.buyselltoggle);
		final EditText et = (EditText) findViewById(R.id.price);

		Button buy = (Button) findViewById(R.id.bsbuy);
		Button sell = (Button) findViewById(R.id.bssell);
		final EditText qtn = (EditText) findViewById(R.id.qtn);

		final String companyName = getIntent().getExtras().getString("Company")
				+ "";
		int i;

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, DBHelper.TB_STOCKS);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner sp = (Spinner) findViewById(R.id.companies);
		sp.setAdapter(adapter);

		bst.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					et.setText("");
					et.setEnabled(false);
				} else {
					et.setEnabled(true);
				}

			}
		});

		for (i = 0; i < DBHelper.TB_STOCKS.length; i++) {

			if (DBHelper.TB_STOCKS[i].equals(companyName.toLowerCase()))
				break;
		}

		if (i >= DBHelper.TB_STOCKS.length)
			i = 0;
		sp.setSelection(i);

		buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(BuySell.this,"AUTOBUY", Toast.LENGTH_LONG).show();
				if (qtn.getText().length() > 0
						&& Integer.parseInt(qtn.getText().toString()) > 0
						) {
					int q = Integer.parseInt(qtn.getText().toString());
					if (bst.isChecked()) {
						
						double price = Companies.PriceList.get(companyName.toLowerCase());
						buyStock(companyName, q,price);
					}
					else{
						SharedPreferences pref= BuySell.this.getSharedPreferences(
										"Autobuy",
										Context.MODE_PRIVATE);
						int count=pref.getInt("autobuycount",0);
						pref.edit().putString("autobuy"+count,companyName.toLowerCase()+"#"+q+"#"+et.getText()).commit();
						count++;
						pref.edit().putInt("autobuycount",count).commit();
						
					}
					

				} else {

					final Dialog dialog = new Dialog(BuySell.this);
					dialog.setTitle("Error");
					LinearLayout linearLayout = new LinearLayout(BuySell.this);
					linearLayout.setPadding(25, 25, 25, 25);
					linearLayout.setOrientation(LinearLayout.VERTICAL);
					TextView desc = new TextView(getApplicationContext());
					desc.setText("Please Enter Proper value for Qtn and price");
					Button ok = new Button(getApplicationContext());
					ok.setText("OK");
					ok.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();

						}
					});
					linearLayout.addView(desc,
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					linearLayout.addView(ok);
					dialog.setContentView(linearLayout);
					dialog.show();
				}

			}
		});

		sell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (qtn.getText().length() > 0
						&& Integer.parseInt(qtn.getText().toString()) > 0
						) {
					int q = Integer.parseInt(qtn.getText().toString());
					if (bst.isChecked()) {
						
						double price = Companies.PriceList.get(companyName.toLowerCase());
						sellStock(companyName, q,price);
					}
					else
					{
						SharedPreferences pref= BuySell.this.getSharedPreferences(
								"Autobuy",
								Context.MODE_PRIVATE);
					int count=pref.getInt("autosellcount",0);
					pref.edit().putString("autosell"+count,companyName.toLowerCase()+"#"+q+"#"+et.getText()).commit();
					count++;
					pref.edit().putInt("autosellcount",count).commit();
					}
				} else {

					final Dialog dialog = new Dialog(BuySell.this);
					dialog.setTitle("Error");
					LinearLayout linearLayout = new LinearLayout(BuySell.this);
					linearLayout.setPadding(25, 25, 25, 25);
					linearLayout.setOrientation(LinearLayout.VERTICAL);
					TextView desc = new TextView(getApplicationContext());
					desc.setText("Please Enter Proper value for Qtn and price");
					Button ok = new Button(getApplicationContext());
					ok.setText("OK");
					ok.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();

						}
					});
					linearLayout.addView(desc,
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					linearLayout.addView(ok);
					dialog.setContentView(linearLayout);
					dialog.show();
				}

			}
		});

	}

	public static void buyStock(String companyName, int q,double price) {

		Companies.updateData(companyName);
		

		SQLiteDatabase d = MainActivity.db.getWritableDatabase();
		String qrl = "select * from userdata where company='"
				+ companyName.toLowerCase() + "'";

		Cursor c = d.rawQuery(qrl, null);

		Log.d("qrl", "buy");

		Log.d("text quantity", q + "");
		Log.d("text current_price", price + "");
		Log.d("text current_amount", price * q + "");
		Log.d("text company", companyName);

		if (c.getCount() > 0) {
			c.moveToFirst();
			Log.d("msg", "Already there update");

			int holding = Integer.parseInt(c.getString(c
					.getColumnIndex("holdings")));
			double amount = Double.parseDouble(c.getString(c
					.getColumnIndex("amount")));
			amount = Math.round(amount * 100.0) / 100.0;

			double namount = q * price;
			namount = Math.round(namount * 100.0) / 100.0;

			double total_amount = amount + namount;

			int nholding = q + holding;

			double navg_price = total_amount / (nholding * 1.0);
			navg_price = Math.round(navg_price * 100.0) / 100.0;

			double nprofit = (price - navg_price) * nholding;
			nprofit = Math.round(nprofit * 100.0) / 100.0;
			Log.d("text current holding", holding + "");
			Log.d("text amount", amount + "");
			Log.d("text namount", namount + "");
			Log.d("text total_amount ", total_amount + "");
			Log.d("text navg_price", navg_price + "");
			Log.d("text nprofit", nprofit + "");

			d.execSQL("UPDATE userdata SET holdings = '" + nholding
					+ "',avg_price = '" + navg_price + "',amount = '"
					+ total_amount + "',profit ='" + nprofit
					+ "' where company='" + companyName.toLowerCase() + "'");

		} else {
			Log.d("msg", "new entry");

			d.execSQL("INSERT into userdata (company,holdings,avg_price,amount,profit) values ('"
					+ companyName.toLowerCase()
					+ "','"
					+ q
					+ "','"
					+ price
					+ "','" + price * q + "','0')");

		}

		Toast.makeText(cont, "Stock Buyed Successfully", Toast.LENGTH_SHORT)
				.show();

		c.close();
		d.close();

	}

	public static void sellStock(String companyName, int q,double price) {
		Companies.updateData(companyName);
		

		SQLiteDatabase d = MainActivity.db.getWritableDatabase();
		String qrl = "select * from userdata where company='"
				+ companyName.toLowerCase() + "'";

		Cursor c = d.rawQuery(qrl, null);

		Log.d("qrl", "sell");

		Log.d("text quantity", q + "");
		Log.d("text current_price", price + "");
		Log.d("text current_amount", price * q + "");
		Log.d("text company", companyName);

		if (c.getCount() > 0) {
			c.moveToFirst();

			int holding = Integer.parseInt(c.getString(c
					.getColumnIndex("holdings")));

			if (holding > q) {

				double amount = Double.parseDouble(c.getString(c
						.getColumnIndex("amount")));
				amount = Math.round(amount * 100.0) / 100.0;

				double namount = q * price;
				namount = Math.round(namount * 100.0) / 100.0;

				double total_amount = amount - namount;

				int nholding = holding - q;

				double navg_price = total_amount / (nholding * 1.0);
				navg_price = Math.round(navg_price * 100.0) / 100.0;

				double nprofit = (price - navg_price) * nholding;
				nprofit = Math.round(nprofit * 100.0) / 100.0;

				Log.d("text current holding", holding + "");
				Log.d("text amount", amount + "");
				Log.d("text namount", namount + "");
				Log.d("text total_amount ", total_amount + "");
				Log.d("text navg_price", navg_price + "");
				Log.d("text nprofit", nprofit + "");

				d.execSQL("UPDATE userdata SET holdings = '" + nholding
						+ "',avg_price = '" + navg_price + "',amount = '"
						+ total_amount + "',profit ='" + nprofit
						+ "' where company='" + companyName.toLowerCase() + "'");

				Toast.makeText(cont, "Stocks sold successfully",
						Toast.LENGTH_SHORT).show();

			} else if (holding == q) {
				// DELETE ROW
				d.execSQL("DELETE from userdata  where company='"
						+ companyName.toLowerCase() + "'");

				Toast.makeText(cont, "All of these stock sold",
						Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(cont, "You Don't have any of these stock",
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(cont, "You Don't have any of these stock",
					Toast.LENGTH_SHORT).show();

		}

		c.close();
		d.close();

	}
}
