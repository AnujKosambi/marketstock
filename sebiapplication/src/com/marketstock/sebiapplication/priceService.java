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
