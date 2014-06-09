package com.marketstock.sebiapplication;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class stockDetail extends SherlockFragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.stock_detail, container, false);
        TextView companyName=(TextView)rootView.findViewById(R.id.Scompany_name);
        TextView companyOpen=(TextView)rootView.findViewById(R.id.Sopen);
        TextView companyClose=(TextView)rootView.findViewById(R.id.Sprev);
        TextView companyHigh=(TextView)rootView.findViewById(R.id.Shigh);
        TextView companyLow=(TextView)rootView.findViewById(R.id.Slow);
        TextView companyVol=(TextView)rootView.findViewById(R.id.Svolume);
        
        companyName.setText(Stockpage.companyName.toUpperCase());
        companyOpen.setText("Open Price :"+Stockpage.stock.getOpenPrice()+"");
        companyClose.setText("Close Price :"+Stockpage.stock.getClosePrice()+"");
        companyHigh.setText("High Price :"+Stockpage.stock.getHighPrice()+"");
        companyLow.setText("Low Price :"+Stockpage.stock.getLowPrice()+"");
        Date date=new Date(Calendar.getInstance().getTimeInMillis());
        
        int vol=(int) ((Stockpage.stock.getVolume()*date.getSeconds())/86400);
        companyVol.setText("Low Price :"+vol+"");
        return rootView;
    }
}
