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
import android.util.Log;

import com.marketstock.sebiapplication.dbhelper.DBHelper;

public class priceService extends IntentService {

	public static float sensex = 15000.00f;

	public static double sensexUpdateArray[] = { 7.36, 3.55, 5.84, 2.53, 2.53,
			3.04, 3.55, 2.28, 18.03, 17.52, 3.04, 2.53, 5.33, 20.57, 14.22,
			22.34, 18.79, 3.8, 6.6, 4.06, 9.9, 22.09, 9.14, 4.82, 5.33, 11.17,
			2.03, 4.57, 12.95, 3.8 };

	public priceService() {
		super("priceService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		while (true) {

			synchronized (this) {
				try {
					float weight = 0.0f;
					float last = 0.0f;
					float cweight = 0.0f;
					for (int i = 0; i < DBHelper.TB_STOCKS.length; i++) {

						float hp = 10, lp = 0;
						Cursor c = MainActivity.db.getReadableDatabase()
								.rawQuery(
										"SELECT highPrice,lowPrice,closePrice from '"
												+ DBHelper.TB_STOCKS[i]
												+ "' order by date ", null);
						c.moveToPosition(MainActivity.moveToDays);
						hp = c.getFloat(0);
						lp = c.getFloat(1);

						if (c.moveToPrevious())
							last = c.getFloat(2);

						ContentValues values = new ContentValues();
						double price = Math.round((lp + Math.random()
								* (hp - lp)) * 100.0) / 100.0;
						
						float pc = (float) ((price - last) / (last * 0.01));
						
						cweight = (float) (pc * sensexUpdateArray[i]);
						
						
						cweight = (float) (Math.round(cweight * 100.0) / 100.0);
						pc = (float) (Math.round(pc * 100.0) / 100.0);

						weight += cweight;

						values.put("price", price);
						values.put("weight", cweight);
						values.put("percentChange", pc);

						MainActivity.db.getWritableDatabase().update(
								DBHelper.TB_COMPANYDATA, values,
								"company = '" + DBHelper.TB_STOCKS[i] + "'",
								null);

					}
					sensex += weight;
					sensex = (float) ((Math.round(sensex) * 100.0) / 100.0);

					weight = (float) (Math.round(weight * 100.0) / 100.0);

					SharedPreferences prefs = getApplicationContext()
							.getSharedPreferences(
									"com.marketstock.sebiapplication",
									Context.MODE_PRIVATE);

					long installed = prefs.getLong("Date", 0);
					prefs.edit().putFloat("sensex", sensex).commit();
					prefs.edit().putFloat("sensexchange", weight).commit();

					SimpleDateFormat formatter = new SimpleDateFormat("dd");
					SimpleDateFormat formatter1 = new SimpleDateFormat("HH");
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(installed);
					String date_install = formatter.format(calendar.getTime());
					Log.e("install_date", date_install);
					Date today = new Date();
					calendar.setTimeInMillis(calendar.getTimeInMillis());
					String hour = formatter1.format(calendar.getTime());
					Log.e("hour", hour);
					String[] temp2 = today.toGMTString().split(" ");
					String opentime = "9";
					String date_today = temp2[0];
					Log.e("Date Today", date_today);
					int diff = Integer.parseInt(date_today)
							- Integer.parseInt(date_install);
					Log.e("diff", diff + "");
					if (hour.equals(opentime)) {
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
