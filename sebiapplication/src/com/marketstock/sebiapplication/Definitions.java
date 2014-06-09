package com.marketstock.sebiapplication;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class Definitions extends SherlockActivity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definitions);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		ImageButton prevbutton = (ImageButton) findViewById(R.id.goprev);
		ImageButton nextbutton = (ImageButton) findViewById(R.id.gonext);
		final EditText definition = (EditText) findViewById(R.id.definition);
		TextView title = (TextView) findViewById(R.id.definiton_title);
		prevbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				definition.setText("Definition goes here");
			}
		});
		
		nextbutton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
					}
				});
		
	}

}
