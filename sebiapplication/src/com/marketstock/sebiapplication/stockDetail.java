package com.marketstock.sebiapplication;

import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class stockDetail extends SherlockFragment{

	public Button buy, sell;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.stock_detail, container, false);
        final TextView companyName=(TextView)rootView.findViewById(R.id.Scompany_name);
        TextView companyOpen=(TextView)rootView.findViewById(R.id.Sopen);
        TextView companyClose=(TextView)rootView.findViewById(R.id.Sprev);
        TextView companyHigh=(TextView)rootView.findViewById(R.id.Shigh);
        TextView companyLow=(TextView)rootView.findViewById(R.id.Slow);
        TextView companyVol=(TextView)rootView.findViewById(R.id.Svolume);
        TextView companyValue=(TextView)rootView.findViewById(R.id.Scompany_price);
        TextView company52high=(TextView)rootView.findViewById(R.id.S52high);
        TextView company52low=(TextView)rootView.findViewById(R.id.S52low);
        TextView companyChange=(TextView)rootView.findViewById(R.id.sCompany_change);
		
        
        companyName.setText(Stockpage.companyName.toUpperCase());
        companyOpen.setText(Stockpage.stock.getOpenPrice()+"");
        companyClose.setText(Stockpage.prevCloseprice+"");
        companyHigh.setText(Stockpage.stock.getHighPrice()+"");
        companyLow.setText(Stockpage.stock.getLowPrice()+"");
        
        companyValue.setText(String.format("%.2f",  Stockpage.companyPrice));
        company52high.setText(String.format("%.2f", Stockpage.companyHigh52));
        company52low.setText(String.format("%.2f",  Stockpage.companyLow52));
        double percent=((Stockpage.companyPrice-Stockpage.prevCloseprice)*100)/(Stockpage.prevCloseprice);
        companyChange.setText(String.format("%.2f",Stockpage.companyPrice-Stockpage.prevCloseprice)
        		+" ("+ String.format("%.2f",percent) +"%)");
       // companyValue.setText()
        Calendar calendar=Calendar.getInstance();
        long nowMillies=(calendar.get(Calendar.HOUR_OF_DAY)*60*60*1000);
        nowMillies+=calendar.get(Calendar.MINUTE)*60*1000;
        long millies330=(long)(15.5*60*60*1000);
        long millies930=(long)(9.5*60*60*1000);
        long durationMillies=millies330-millies930;
        int vol=0;//=(int) ((Stockpage.stock.getVolume()*nowMillies)/millies330);
        if(nowMillies<millies930)
        {
        	vol=0;
        }
        else if(nowMillies>millies330)
        {
        	nowMillies=millies330;
        }
        else
        {
        	vol=(int) ((Stockpage.stock.getVolume()*(nowMillies-millies930))/durationMillies);
        }
        Toast.makeText(rootView.getContext(), Stockpage.stock.getVolume()+"", Toast.LENGTH_LONG).show();
        companyVol.setText(vol+" ");
        
        
        buy = (Button) rootView.findViewById(R.id.button1);
		sell = (Button) rootView.findViewById(R.id.button2);
		
		buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(rootView.getContext(),BuySell.class);				
				intent.putExtra("Company", companyName.getText());
				startActivity(intent);
				
			}
		});
		
        return rootView;
    }
}
