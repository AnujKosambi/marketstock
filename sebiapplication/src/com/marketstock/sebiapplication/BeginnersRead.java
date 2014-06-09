package com.marketstock.sebiapplication;

import android.content.Context;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class BeginnersRead extends SherlockActivity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context =this;
		setContentView(R.layout.beginnersread);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
