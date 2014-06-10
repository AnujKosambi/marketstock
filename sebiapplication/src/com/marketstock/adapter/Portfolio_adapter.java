package com.marketstock.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marketstock.sebiapplication.R;


public class Portfolio_adapter extends BaseAdapter{
	 private Context activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	 
	    public Portfolio_adapter(Context a, ArrayList<HashMap<String, String>> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    @Override
		public int getCount() {
	        return data.size();
	    
	    }
	    @Override
		public Object getItem(int position) {
	        return position;
	    }
	 
	    @Override
		public long getItemId(int position) {
	        return position;
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        Holder holder = new Holder();
        if(vi==null){
        	holder = new Holder();
            vi = inflater.inflate(R.layout.portfolio_row_layout, parent,false);
            holder.scrip = (TextView)vi.findViewById(R.id.portfolio_scrip); //name
            holder.holding = (TextView)vi.findViewById(R.id.portfolio_holding); 
            holder.avg_price = (TextView)vi.findViewById(R.id.portfolio_avg_price);
            holder.amount = (TextView)vi.findViewById(R.id.portfolio_amount); 
            holder.profit = (TextView)vi.findViewById(R.id.portfolio_profit);             
            vi.setTag(holder);
            
        }
        else{
        	holder = (Holder)vi.getTag();
        }
        HashMap<String, String> index = new HashMap<String, String>();
        index = data.get(position);
 
        // Setting all values in list view
        holder.scrip.setText(index.get(com.marketstock.sebiapplication.portFolio.KEY_NAME));
        holder.holding.setText(index.get(com.marketstock.sebiapplication.portFolio.KEY_HOLDING));
        holder.avg_price.setText(index.get(com.marketstock.sebiapplication.portFolio.KEY_AVG_PRICE));
        holder.amount.setText(index.get(com.marketstock.sebiapplication.portFolio.KEY_AMOUNT));
        holder.profit.setText(index.get(com.marketstock.sebiapplication.portFolio.KEY_PROFIT));
        
        if(Double.parseDouble(index.get(com.marketstock.sebiapplication.portFolio.KEY_PROFIT))>0){
        	vi.setBackgroundColor(Color.rgb(82,252,82));
        }else{
        	vi.setBackgroundColor(Color.rgb(252,82,82));
        }
        
        return vi;
        
    }
	
	private static class Holder{
		TextView scrip;
		TextView holding;
		TextView avg_price;
		TextView amount;
		TextView profit;
	}

}
