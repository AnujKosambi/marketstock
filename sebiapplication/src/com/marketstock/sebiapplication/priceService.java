package com.marketstock.sebiapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.marketstock.sebiapplication.dbhelper.DBHelper;

public class priceService extends IntentService {

	public priceService() {
		super("priceService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		while (true) {

			synchronized (this) {
				try {
					for (int i = 0; i < DBHelper.TB_STOCKS.length; i++) {
						float hp = 10, lp = 0;
						Cursor c = MainActivity.db.getReadableDatabase()
								.rawQuery(
										"SELECT highPrice,lowPrice from '"
												+ DBHelper.TB_STOCKS[i]
												+ "' order by date ", null);
						c.moveToPosition(MainActivity.moveToDays);
						hp = c.getFloat(0);
						lp = c.getFloat(1);

						ContentValues values = new ContentValues();
						double price = Math.round((lp + Math.random()
								* (hp - lp)) * 100.0) / 100.0;

						values.put("price", price);
						MainActivity.db.getWritableDatabase().update(
								DBHelper.TB_COMPANYDATA, values,
								"company = '" + DBHelper.TB_STOCKS[i] + "'",
								null);
					}
					SharedPreferences prefs =getApplicationContext().getSharedPreferences(
						      "com.marketstock.sebiapplication", Context.MODE_PRIVATE);
					long installed = prefs.getLong("Date",0);
					SimpleDateFormat formatter = new SimpleDateFormat("dd");
					SimpleDateFormat formatter1 = new SimpleDateFormat("HH");
				     Calendar calendar = Calendar.getInstance();
				     calendar.setTimeInMillis(installed);
				     String date_install = formatter.format(calendar.getTime());
				     Log.e("install_date",date_install );
				     Date today = new Date();
				     calendar.setTimeInMillis(calendar.getTimeInMillis());
				     String hour = formatter1.format(calendar.getTime());
				     Log.e("hour", hour);
				     String[] temp2 = today.toGMTString().split(" ");
				     String opentime = "9";
				     String date_today = temp2[0];
				     Log.e("Date Today", date_today);
				     int diff = Integer.parseInt(date_today) - Integer.parseInt(date_install);
				     Log.e("diff",diff+"");
				     if(hour.equals(opentime))
				     {
				    	 TipOfDay tip = new TipOfDay();
				    	 tip.inflate_tips(diff);
				     }
				     wait(60000);
				} catch (Exception e) {
				}
			}
		}

	}

}
