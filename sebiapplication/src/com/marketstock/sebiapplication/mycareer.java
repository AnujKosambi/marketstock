package com.marketstock.sebiapplication;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class mycareer extends SherlockActivity {

	TextView level1;
	TextView threshold1;
	TextView unlocks1;
	TextView bonus1;
	
	TextView level2;
	TextView threshold2;
	TextView unlocks2;
	TextView bonus2;
	
	TextView level3;
	TextView threshold3;
	TextView unlocks3;
	TextView bonus3;
	
	TextView level4;
	TextView threshold4;
	TextView unlocks4;
	TextView bonus4;
	
	TextView level5;
	TextView threshold5;
	TextView unlocks5;
	TextView bonus5;
	
	TextView level6;
	TextView threshold6;
	TextView unlocks6;
	TextView bonus6;
	
	TextView level7;
	TextView threshold7;
	TextView unlocks7;
	TextView bonus7;
	
	TextView level8;
	TextView threshold8;
	TextView unlocks8;
	TextView bonus8;
	
	TextView level9;
	TextView threshold9;
	TextView unlocks9;
	TextView bonus9;
	
	TextView level10;
	TextView threshold10;
	TextView unlocks10;
	TextView bonus10;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context = getApplication();
		setContentView(R.layout.mycareer);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	}
}
