package com.marketstock.helper;

import java.sql.Date;
import java.util.HashMap;

import android.database.Cursor;

import com.marketstock.sebiapplication.MainActivity;
import com.marketstock.sebiapplication.dbhelper.DBHelper;
import com.marketstock.sebiapplication.models.Stock;




public class Companies {
	public static int INFOSYS=0;
	public static HashMap<String,Stock> StockList=new HashMap<String, Stock>();
	public static HashMap<String,Double> prevPriceList=new HashMap<String, Double>();
	public static HashMap<String,Double> PriceList=new HashMap<String, Double>();
	public static HashMap<String,Double> low52=new HashMap<String, Double>();
	public static HashMap<String,Double> high52=new HashMap<String, Double>();
	
	
	public static String[] getCompanies()
	{
		return DBHelper.TB_STOCKS;
	}
	public static void updateData(String companyName){
		int moveToDays=MainActivity.moveToDays;
		
		Cursor cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM "+ companyName+" order by date",null);
		cursor.moveToPosition(moveToDays);
		
		Stock stock=new Stock(new Date(cursor.getLong(1)), 
			cursor.getDouble(cursor.getColumnIndex("openPrice")),
			cursor.getDouble(cursor.getColumnIndex("closePrice")),
			cursor.getDouble(cursor.getColumnIndex("highPrice")), 
			cursor.getDouble(cursor.getColumnIndex("lowPrice")),
			cursor.getDouble(cursor.getColumnIndex("volume")));
		if(StockList.containsKey(companyName))
			StockList.remove(companyName);
		StockList.put(companyName, stock);
		cursor.moveToPrevious();
		
		if(prevPriceList.containsKey(companyName))
			prevPriceList.remove(companyName);
		prevPriceList.put(companyName, cursor.getDouble(3));
		
		cursor.close();
		
		cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM companydata where company='"+companyName+"'",null);
		cursor.moveToFirst();
		if(PriceList.containsKey(companyName))
			PriceList.remove(companyName);
		PriceList.put(companyName, cursor.getDouble(1));
		cursor.close();
	
		
		
	}
	public static void updateData52(String companyName){
		Cursor cursor = MainActivity.db.getReadableDatabase()
				.rawQuery("SELECT * FROM "+ companyName+" order by date",null);
		cursor.moveToPosition(MainActivity.moveToDays);
		float low=Float.MAX_VALUE;
		float high=Float.MIN_VALUE;
		for(;!cursor.isBeforeFirst();)
		{
			low=Math.min(low,(float)cursor.getDouble(cursor.getColumnIndex("lowPrice")));
			high=Math.max(high,(float)cursor.getDouble(cursor.getColumnIndex("highPrice")));
			
			cursor.moveToPrevious();
		}
		if(low52.containsKey(companyName))
			low52.remove(companyName);
		low52.put(companyName, (double)low);
		if(high52.containsKey(companyName))
		high52.remove(companyName);
		high52.put(companyName, (double)high);
		cursor.close();
		
	}
	
}
