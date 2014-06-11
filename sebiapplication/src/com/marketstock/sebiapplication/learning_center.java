package com.marketstock.sebiapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;

public class learning_center extends SherlockFragment{

	Context context = getActivity();

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.cards_learning_center, container, false);
        LinearLayout definitionCard=(LinearLayout)rootView.findViewById(R.id.definitionsCard);
        LinearLayout beginnersCard=(LinearLayout)rootView.findViewById(R.id.beginnersCard);
        LinearLayout tipsCard = (LinearLayout)rootView.findViewById(R.id.tipsCard);
        LinearLayout explanationCard = (LinearLayout)rootView.findViewById(R.id.explanationCard);
        final Context context=getActivity().getApplicationContext();
        
        definitionCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, Definitions.class);
				startActivity(intent);
			}
		});
        
        beginnersCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, BeginnersRead.class);
				startActivity(intent);
			}
		});
        
        tipsCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, TipOfDay.class);
				startActivity(intent);
			}
		});
        
        explanationCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, News.class);
				startActivity(intent);
			}
		});
        
      
        return rootView;
    }

}
