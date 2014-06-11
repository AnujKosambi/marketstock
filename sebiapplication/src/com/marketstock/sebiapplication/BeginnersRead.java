package com.marketstock.sebiapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class BeginnersRead extends SherlockActivity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beginnersread);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		LinearLayout select_stockCard=(LinearLayout)findViewById(R.id.select_stockCard);
        LinearLayout interpret_fr_Card=(LinearLayout)findViewById(R.id.interpret_fr_Card);
        LinearLayout download_fr_Card = (LinearLayout)findViewById(R.id.downlaod_fr_Card);
        
        select_stockCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Select_Stock.class);
				startActivity(intent);
			}
		});
        
		interpret_fr_Card.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplicationContext(), Interpret_Report.class);
						startActivity(intent);
					}
				});

		download_fr_Card.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Download_PDF.class);
				startActivity(intent);
			}
		});
		
	}
}
