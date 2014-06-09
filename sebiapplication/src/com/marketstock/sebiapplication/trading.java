package com.marketstock.sebiapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;

public class trading extends SherlockFragment{
	
	Context context = getActivity();

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.card, container, false);
        LinearLayout indicesCard=(LinearLayout)rootView.findViewById(R.id.indicesCard);
        LinearLayout moversCard=(LinearLayout)rootView.findViewById(R.id.moversCard);
        LinearLayout newsCard = (LinearLayout)rootView.findViewById(R.id.newsCard);
        LinearLayout portfolioCard = (LinearLayout)rootView.findViewById(R.id.portfolioCard);
        final Context context=getActivity().getApplicationContext();
        indicesCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, indices.class);
				startActivity(intent);
			}
		});
        
        moversCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, marketMovers.class);
				startActivity(intent);
			}
		});
        
        newsCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, News.class);
				startActivity(intent);
			}
		});
        
        portfolioCard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, portFolio.class);
				startActivity(intent);
			}
		});
        return rootView;
    }
}