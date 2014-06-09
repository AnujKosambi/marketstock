package com.marketstock.sebiapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BuySell extends Activity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_sell);
		
		String companyName = getIntent().getExtras().getString("Company") + "";
		
		Log.d("CN", companyName);
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(companyName);
	
	}

}
