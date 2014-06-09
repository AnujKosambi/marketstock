package com.marketstock.helper;

import com.marketstock.sebiapplication.dbhelper.DBHelper;




public class Companies {
	public static int INFOSYS=0;
	public static String[] getCompanies()
	{
		return DBHelper.TB_STOCKS;
	}
	
	
}
