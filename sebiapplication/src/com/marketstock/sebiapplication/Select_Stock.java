package com.marketstock.sebiapplication;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class Select_Stock extends SherlockActivity {
	
	int index=1;
	TextView def_title;
	TextView explanation;
	ImageButton next_Button;
	ImageButton prev_Button;
	HashMap<Integer, String> map1;
	HashMap<Integer, String> map2;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosing_stock);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		def_title = (TextView) findViewById(R.id.def_title);
		explanation = (TextView) findViewById(R.id.explanation);
		next_Button = (ImageButton) findViewById(R.id.next);
		prev_Button = (ImageButton) findViewById(R.id.prev);
		
		String title1 = "Start with Bluechip";
		String title2 = "P/E Ratio";
		String title3 = "Book Value";
		String title4 = "High Volume";
		String title5 = "Diversed Portfolio";
		
		String bluechip = "Its always safe for beginners to start with bluechip (the companies which are renowned and which have given good returns in the past) companies.";
		String PE_ratio1 = "P/E ratio is an important indicator. A very high P/E ratio might signal that the shares are overpriced and a very low P/E ratio might be because there are few buyers of that company for some reasons.";
		String PE_ratio2 = "You are advised to do background research in both cases.";
		String book_value = "A high book value is always desirable.";
		String high_vol = "Always trade in those companies which have high ‘volumes’ in the market. Volumes indicate how many shares are traded. ";
		String high_vol2 = "If the volume is too less than this definitely means that there are few people trading in this stock."; 
		String high_vol3 = "This shows a lack of investors’ interest in that company.";
		
		String diversed_portfolio = "Do not put all your money in a single stock. Its always better to have a diversified portfolio so that a sudden negative development for one sector doesn’t eat up all your profits.";
		map1 = new HashMap<Integer, String>();
		map2 = new HashMap<Integer, String>();
		map2.put(1, bluechip);
		map2.put(2, PE_ratio1+"  "+PE_ratio2);
		map2.put(3, book_value);
		map2.put(4, high_vol + high_vol2 + high_vol3);
		map2.put(5, diversed_portfolio);
		
		map1.put(1, title1);
		map1.put(2, title2);
		map1.put(3, title3);
		map1.put(4, title4);
		map1.put(5, title5);
		
		def_title.setText(map1.get(index));
		explanation.setText(map2.get(index));
		check_index(index);
		prev_Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				index--;
				check_index(index);
				def_title.setText(map1.get(index));
				explanation.setText(map2.get(index));
			}
		});
		
		next_Button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						index++;
						check_index(index);
						def_title.setText(map1.get(index));
						explanation.setText(map2.get(index));
					}
				});
		 
		
	}
	
	public void check_index(int index)
	{
		if(index==1)
		{
			prev_Button.setVisibility(View.INVISIBLE);
		}
		else if(index==5)
		{
			next_Button.setVisibility(View.INVISIBLE);
		}
		else
		{
			next_Button.setVisibility(View.VISIBLE);
			prev_Button.setVisibility(View.VISIBLE);
		}
	}
	
}
