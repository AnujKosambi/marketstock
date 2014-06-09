package com.marketstock.sebiapplication.dbhelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	Context c;

	public static final String DB_NAME = "marketstock.db";

	public static final String TB_NEWS = "infosysnew";
	public static final String[] TB_STOCKS = { "axis", "bajaj", "bharti",
			"bhel", "cipla", "coalindia", "drreddy", "gail", "hdfcbank",
			"hdfc", "hero", "hindalco", "hul", "icicibank", "infosys", "itc",
			"lt", "maruti", "mm", "ntpc", "ongc", "ril", "sbi", "sesa",
			"sunpharma", "tatamotors", "tatapower", "tatasteel", "tsc", "wipro" };
	public static final String TB_USERDATA = "userdata";
	public static final String TB_COMPANYDATA = "companydata";
	public static final String TB_TIPS = "tips";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		c = context;
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		c = context;

	}

	private static final String CREATE_TB_NEWS = "CREATE TABLE "
			+ TB_NEWS
			+ " (id INTEGER PRIMARY KEY, title TEXT, desc TEXT, effect TEXT, learning TEXT, fluctuation TEXT)";

	private static final String CREATE_TB_UD = "CREATE TABLE "
			+ TB_USERDATA
			+ " (company TEXT, holdings TEXT, avg_price TEXT, amount TEXT, profit TEXT)";

	private static final String CREATE_TB_CD = "CREATE TABLE " + TB_COMPANYDATA
			+ " (company TEXT, price REAL)";

	private static final String CREATE_TB_TIPS = "CREATE TABLE " + TB_TIPS
			+ " (id INTEGER PRIMARY KEY, tips TEXT)";

	private BufferedReader buffer;

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TB_NEWS);
		db.execSQL(CREATE_TB_UD);
		db.execSQL(CREATE_TB_CD);
		db.execSQL(CREATE_TB_TIPS);
		db.setMaximumSize(10240*1024);
		SharedPreferences prefs = c.getSharedPreferences(
				"com.marketstock.sebiapplication", Context.MODE_PRIVATE);
		prefs.edit()
				.putLong("Date", Calendar.getInstance().getTime().getTime())
				.commit();
		InputStreamReader inputstream;
		String line, tableName, columns, str1, str2;

		for (int i = 0; i < TB_STOCKS.length; i++) {
			String CREATE_TB_INFOSYS = "CREATE TABLE "
					+ TB_STOCKS[i]
					+ " (id INTEGER PRIMARY KEY, date TEXT, openPrice REAL, closePrice REAL, highPrice REAL, lowPrice REAL, volume REAL)";

			db.execSQL(CREATE_TB_INFOSYS);

			str1 = "INSERT INTO " + TB_COMPANYDATA + " (company, price"
					+ ") values('";
			str2 = "',0);";

			db.execSQL(str1 + TB_STOCKS[i] + str2);

		}

		try {

			for (int i = 0; i < TB_STOCKS.length; i++) {

				inputstream = new InputStreamReader(c.getAssets().open(
						TB_STOCKS[i] + ".csv"));
				buffer = new BufferedReader(inputstream);
				line = "";
				tableName = TB_STOCKS[i];
				columns = " date, openPrice, highPrice, lowPrice, closePrice,volume,id ";
				str1 = "INSERT INTO " + tableName + " (" + columns
						+ ") values(";
				str2 = ");";
				int j = 1;
				db.beginTransaction();
				while ((line = buffer.readLine()) != null) {
					StringBuilder sb = new StringBuilder(str1);
					String[] str = line.split(",");
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

					sb.append("'" + format.parse(str[0]).getTime() + "',");
					sb.append(str[1] + ",");
					sb.append(str[2] + ",");
					sb.append(str[3] + ",");
					sb.append(str[4] + ",");
					sb.append(str[5] + ",");
					sb.append(j++);

					sb.append(str2);
					db.execSQL(sb.toString());

				}
				db.setTransactionSuccessful();
				db.endTransaction();

			}

			inputstream = new InputStreamReader(c.getAssets().open(
					TB_TIPS + ".csv"));
			buffer = new BufferedReader(inputstream);
			line = "";
			tableName = TB_TIPS;
			columns = " id,tips ";
			str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
			str2 = ");";
			
			db.beginTransaction();

			while ((line = buffer.readLine()) != null) {
				StringBuilder sb = new StringBuilder(str1);
				String[] str = line.split(",,");
				
				sb.append(str[0] + ",");
				sb.append(str[1]);
				sb.append(str2);
				Log.d(sb.toString(),"q");
				db.execSQL(sb.toString());
			}
			db.setTransactionSuccessful();
			db.endTransaction();

			inputstream = new InputStreamReader(c.getAssets().open(
					"infosysNews.csv"));
			buffer = new BufferedReader(inputstream);
			line = "";
			tableName = "infosysnew";
			columns = " title, desc, learning, effect, fluctuation,id ";
			str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
			str2 = ");";

			db.beginTransaction();
			int id = 1;
			while ((line = buffer.readLine()) != null) {
				StringBuilder sb = new StringBuilder(str1);
				String[] str = line.split(",,");

				sb.append(str[0] + ",");
				sb.append(str[1] + ",");
				sb.append(str[2] + ",");
				sb.append(str[3] + ",");
				sb.append(str[4] + ",");
				sb.append(id++);

				sb.append(str2);
				db.execSQL(sb.toString());
				
			}
			db.setTransactionSuccessful();
			db.endTransaction();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TB_NEWS);
		for (int i = 0; i < TB_STOCKS.length; i++) {
			db.execSQL("DROP TABLE IF EXISTS " + TB_STOCKS[i]);
		}

		onCreate(db);

	}

}
