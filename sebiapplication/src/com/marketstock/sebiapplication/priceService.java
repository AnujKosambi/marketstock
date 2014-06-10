package com.marketstock.sebiapplication;

import java.io.ObjectOutputStream.PutField;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
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
				     Calendar calendar = Calendar.getInstance();
				     calendar.setTimeInMillis(installed);
				     String date_install = formatter.format(calendar.getTime());
				     Log.e("install_date",date_install );
				     Date today = new Date();
				     calendar.setTimeInMillis(calendar.getTimeInMillis());
				     Calendar calendar1 = Calendar.getInstance();
				     int hour = calendar1.get(calendar1.HOUR_OF_DAY);
				     int min = calendar1.get(calendar1.MINUTE);
				     int sec = calendar1.get(calendar1.SECOND);
				     Log.e("hour", hour+"");
				     Log.e("min", min+"");
				     String[] temp2 = today.toGMTString().split(" ");
				     int opentime = 9;
				     String date_today = temp2[0];
				     Log.e("Date Today", date_today);
				     int diff = Integer.parseInt(date_today) - Integer.parseInt(date_install);
				     Log.e("diff",diff+"");
				     SharedPreferences prefer =getApplicationContext().getSharedPreferences(
						      "com.marketstock.sebiapplication", Context.MODE_PRIVATE);
				     prefer.edit().putInt("index", diff);
				     
				     if(hour == opentime && min<1)
				     {
				    	 Cursor cursor = MainActivity.db.getReadableDatabase()
									.rawQuery("SELECT * FROM "+ "tips",null); 
				    	 cursor.moveToPosition(diff);
				    	 Intent resultIntent = new Intent(this, TipOfDay.class);
							TaskStackBuilder stackBuilder = TaskStackBuilder.create(this)
									.addParentStack(TipOfDay.class)
									.addNextIntent(resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
							PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

							Log.e("text", cursor.getString(1));
						 Builder notif = new NotificationCompat.Builder(this);
					        notif.setContentIntent(resultPendingIntent)
					                .setWhen(System.currentTimeMillis())
					                .setTicker("Application Name")
					                .setSmallIcon(R.drawable.ic_launcher)
					                .setContentTitle("Tip of the Day")
					                .setContentText(cursor.getString(1))
					                .setAutoCancel(true);
					      //create notification from builder
					      Notification notification = notif.build();
					      //get instance of NotificationManager
					      NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					      //call notify method of NotificationManager to add this notification to android notification drawer..
					      notificationmanager.notify(0, notification);
				     }
				     
				     wait(60000);

				} catch (Exception e) {
				}
			}
		}

	}

}
