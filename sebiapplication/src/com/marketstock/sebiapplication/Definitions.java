package com.marketstock.sebiapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class Definitions extends SherlockActivity{
	
	public int index = 1;
	public Cursor cursor;
	TextView title;
	TextView definition;
	ImageButton prevbutton;
	ImageButton nextbutton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definitions);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		prevbutton = (ImageButton) findViewById(R.id.goprev);
		nextbutton = (ImageButton) findViewById(R.id.gonext);
		definition = (TextView) findViewById(R.id.definition);
		title = (TextView) findViewById(R.id.definiton_title);
		check_index(index);
		cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM "+ "definition",null); 
		cursor.moveToFirst();
		
		title.setText(cursor.getString(1).replaceAll("//", "'"));
		definition.setText(cursor.getString(2).replaceAll("//", "'"));
		
		prevbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				index--;
				check_index(index);
				cursor.moveToPosition(index-1);
				title.setText(cursor.getString(1).replaceAll("//", "'"));
				definition.setText(cursor.getString(2).replaceAll("//", "'"));
			}
		});
		
		nextbutton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						index++;
						check_index(index);
						cursor.moveToPosition(index-1);
						title.setText(cursor.getString(1).replaceAll("//", "'"));
						definition.setText(cursor.getString(2).replaceAll("//", "'"));
					}
				});
		
	}
	
	public void check_index(int index)
	{
		if(index==1)
		{
			prevbutton.setVisibility(View.INVISIBLE);
		}
		else if(index==12)
		{
			nextbutton.setVisibility(View.INVISIBLE);
		}
		else
		{
			nextbutton.setVisibility(View.VISIBLE);
			prevbutton.setVisibility(View.VISIBLE);
		}
	}

}
