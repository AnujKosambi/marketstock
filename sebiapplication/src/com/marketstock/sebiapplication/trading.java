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
	LinearLayout card_indices;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.card, container, false);
        LinearLayout indicesCard=(LinearLayout)rootView.findViewById(R.id.indicesCard);
        final Context context=getActivity().getApplicationContext();
        indicesCard.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				Intent intent = new Intent(context, indices.class);
				startActivity(intent);
				return false;

			}
		});
        return rootView;
    }
}