package com.marketstock.sebiapplication;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.marketstock.adapter.BaseInflaterAdapter;
import com.marketstock.adapter.CardItemData;
import com.marketstock.adapter.inflaters.CardInflater;

public class TipOfDay extends SherlockActivity {
	ListView list;
	Context context = getApplication();
	 public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Context context =this;
			setContentView(R.layout.tipofday);
			ActionBar actionBar=getSupportActionBar();
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			
				
	 }
	 
	 public void inflate_tips(int diff)
	 {
		 	list = (ListView) findViewById(R.id.tip_list_view);
			list.addHeaderView(new View(context));
			list.addFooterView(new View(context));

			BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
			Cursor cursor = MainActivity.db.getReadableDatabase()
					.rawQuery("SELECT * FROM "+ "tips",null); 
			cursor.moveToFirst();
			while(cursor.isAfterLast()==false || diff>=0)
			{
				CardItemData data = new CardItemData(cursor.getString(1));
				adapter.addItem(data, false);
				cursor.moveToNext();
				diff--;
			}
			list.setAdapter(adapter);
			generateTipsNotification(context, adapter.getItem(diff).toString());
	 }
	 
	 public void generateTipsNotification(Context context, String text){
		Intent resultIntent = new Intent(context, TipOfDay.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context)
					.addParentStack(TipOfDay.class)
					.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		 Builder notif = new NotificationCompat.Builder(context);
	        notif.setContentIntent(resultPendingIntent)
	                .setWhen(System.currentTimeMillis())
	                .setTicker("Application Name")
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setContentTitle("Tip of the Day")
	                .setContentText(text)
	                .setAutoCancel(true);
	      //create notification from builder
	      Notification notification = notif.build();
	      //get instance of NotificationManager
	      NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	      //call notify method of NotificationManager to add this notification to android notification drawer..
	      notificationmanager.notify(0, notification);
		}

}
