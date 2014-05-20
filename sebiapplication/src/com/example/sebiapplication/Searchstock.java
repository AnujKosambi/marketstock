package com.example.sebiapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class Searchstock extends Activity {
	
	private Button searchStockBtn;
	private AutoCompleteTextView stockVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchstock);
		searchStockBtn = (Button) findViewById(R.id.searchstockbtn);
		stockVal = (AutoCompleteTextView) findViewById(R.id.stockval);
		
		searchStockBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),Stockpage.class));
				Toast.makeText(getApplicationContext(),"Search the stock: "+stockVal.getText() , Toast.LENGTH_SHORT).show();
			}
		});
		
	}
}
