package com.marketstock.sebiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.SherlockActivity;

public class SplashScreen extends SherlockActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		new Handler().postDelayed(new Runnable() {

			public void run() {

				Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
				startActivity(mainIntent);
				
				

				finish();
				overridePendingTransition(R.anim.mainfadein,
						R.anim.splashfadeout);
			}
		}, 2000);

		
		//startActivity(new Intent(this,MainActivity.class));
	
		
	}
}
