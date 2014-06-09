package com.marketstock.sebiapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.marketstock.sebiapplication.dbhelper.DBHelper;

public class Searchstock extends Activity {

	private Button searchStockBtn;
	private AutoCompleteTextView stockVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchstock);
		searchStockBtn = (Button) findViewById(R.id.searchstockbtn);
		stockVal = (AutoCompleteTextView) findViewById(R.id.stockval);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, DBHelper.TB_STOCKS);
		stockVal.setAdapter(adapter);


		searchStockBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						Stockpage.class);
				intent.putExtra("Company", stockVal.getText().toString());
				startActivity(intent);
				Toast.makeText(getApplicationContext(),
						"Search the stock: " + stockVal.getText(),
						Toast.LENGTH_SHORT).show();
			}
		});

	}
}
