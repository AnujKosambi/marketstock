package com.marketstock.sebiapplication;

import java.util.Calendar;
import java.util.Date;

import android.R.string;
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
        Date date=new Date(Calendar.getInstance().getTimeInMillis());
        
        int vol=(int) ((Stockpage.stock.getVolume()*date.getTime())/86400000);
        companyVol.setText(vol+"");
        return rootView;
    }
}
