package com.marketstock.sebiapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;

public class trading extends SherlockFragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.card, container, false);
        LinearLayout indicesCard=(LinearLayout)rootView.findViewById(R.id.indicesCard);
        indicesCard.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				return false;
			}
		});
        return rootView;
    }
}
