package com.marketstock.sebiapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class priceService extends IntentService{

	public priceService() {
		super("priceService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Toast.makeText(getApplicationContext(), "t4t", Toast.LENGTH_SHORT).show();
		Log.d("dwef", "fef");
	     long endTime = System.currentTimeMillis() + 5*1000;
	      while (System.currentTimeMillis() < endTime) {
	          synchronized (this) {
	              try {
	                  wait(endTime - System.currentTimeMillis());
	              } catch (Exception e) {
	              }
	          }
	      }
		
	}

}
