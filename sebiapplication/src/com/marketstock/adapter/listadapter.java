package com.marketstock.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.sebiapplication.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class listadapter extends BaseAdapter{
	 private Context activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	 
	    public listadapter(Context a, ArrayList<HashMap<String, String>> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    public int getCount() {
	        return data.size();
	    }
	 
	    public Object getItem(int position) {
	        return position;
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        Holder holder = new Holder();
        if(vi==null){
        	Log.e("vi not null","");
        	holder = new Holder();
            vi = inflater.inflate(R.layout.indices_row_layout, parent,false);
            holder.name = (TextView)vi.findViewById(R.id.company_name); //name
            holder.date = (TextView)vi.findViewById(R.id.indices_date); 
            holder.value = (TextView)vi.findViewById(R.id.indices_value); 
            holder.pointchange = (TextView)vi.findViewById(R.id.indices_pointchange);
            holder.percentchange = (TextView)vi.findViewById(R.id.indices_percentchange); 
            vi.setTag(holder);
            
        }
        else{
        	holder = (Holder)vi.getTag();
        }
        Log.e("Vi value", vi.toString());
        HashMap<String, String> index = new HashMap<String, String>();
        index = data.get(position);
 
        // Setting all values in list view
        holder.name.setText(index.get(com.marketstock.sebiapplication.indices.KEY_NAME));
        holder.date.setText(index.get(com.marketstock.sebiapplication.indices.KEY_DATE));
        holder.value.setText(index.get(com.marketstock.sebiapplication.indices.KEY_VALUE));
        holder.pointchange.setText(index.get(com.marketstock.sebiapplication.indices.KEY_POINT_CHANGE));
        holder.percentchange.setText(index.get(com.marketstock.sebiapplication.indices.KEY_PERCENT_CHANGE));
        return vi;
        
    }
	
	private static class Holder{
		TextView name;
		TextView date;
		TextView value;
		TextView pointchange;
		TextView percentchange;
	}

}
