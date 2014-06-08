package com.marketstock.sebiapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;

public class trading extends SherlockFragment{
	
	Context context = getActivity();
	LinearLayout card_indices;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cards_trading_home, container, false);
        card_indices = (LinearLayout) rootView.findViewById(R.id.cards_trading_indices);
        card_indices.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment index = new indices();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(((ViewGroup)getView().getParent()).getId(), index);
				transaction.addToBackStack(null);
			    transaction.commit(); 
				
			}
		});
        return rootView;
    }
}
