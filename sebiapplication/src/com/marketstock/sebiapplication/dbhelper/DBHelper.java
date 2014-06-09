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

public class DBHelper extends SQLiteOpenHelper {
	Context c;

	public static final String DB_NAME = "marketstock.db";

	public static final String TB_NEWS[] = { "infosysnew", "bajajautonews",
			"tcsnews" };
	public static final String TB_COMMON_NEWS = "commonnews";
	public static final String[] TB_STOCKS = { "axis", "bajaj", "bharti",
			"bhel", "cipla", "coalindia", "drreddy", "gail", "hdfcbank",
			"hdfc", "hero", "hindalco", "hul", "icicibank", "infosys", "itc",
			"lt", "maruti", "mm", "ntpc", "ongc", "ril", "sbi", "sesa",
			"sunpharma", "tatamotors", "tatapower", "tatasteel", "tcs", "wipro" };
	public static final String TB_USERDATA = "userdata";
	public static final String TB_COMPANYDATA = "companydata";
	public static final String TB_TIPS = "tips";
	public static final String TB_DF = "definition";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		c = context;
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		c = context;

	}

	private static final String CREATE_TB_UD = "CREATE TABLE "
			+ TB_USERDATA
			+ " (company TEXT, holdings TEXT, avg_price TEXT, amount TEXT, profit TEXT)";

	private static final String CREATE_TB_CD = "CREATE TABLE " + TB_COMPANYDATA
			+ " (company TEXT, price REAL)";

	private static final String CREATE_TB_CN = "CREATE TABLE "
			+ TB_COMMON_NEWS
			+ " (id INTEGER PRIMARY KEY,company TEXT, title TEXT, desc TEXT, learning TEXT, day INTEGER)";

	private static final String CREATE_TB_TIPS = "CREATE TABLE " + TB_TIPS
			+ " (id INTEGER PRIMARY KEY, tips TEXT)";

	private static final String CREATE_TB_DF = "CREATE TABLE " + TB_DF
			+ " (term TEXT,definition TEXT)";

	private BufferedReader buffer;

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.setMaximumSize(10240 * 1024);

		db.execSQL(CREATE_TB_UD);
		db.execSQL(CREATE_TB_CD);
		db.execSQL(CREATE_TB_CN);
		db.execSQL(CREATE_TB_TIPS);
		db.execSQL(CREATE_TB_DF);

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

		for (int i = 0; i < TB_NEWS.length; i++) {

			String CREATE_TB_NEWS = "CREATE TABLE "
					+ TB_NEWS[i]
					+ " (id INTEGER PRIMARY KEY, title TEXT, desc TEXT, effect TEXT, learning TEXT, fluctuation TEXT,day INTEGER)";

			db.execSQL(CREATE_TB_NEWS);

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

			String table;
			for (int i = 0; i < 2; i++) {

				if (i == 0) {
					table = TB_TIPS;
					columns = " id,tips ";
				} else {
					table = TB_DF;
					columns = " term,definition ";
				}

				inputstream = new InputStreamReader(c.getAssets().open(
						table + ".csv"));

				buffer = new BufferedReader(inputstream);
				line = "";

				str1 = "INSERT INTO " + table + " (" + columns + ") values(";
				str2 = ");";

				db.beginTransaction();

				while ((line = buffer.readLine()) != null) {

					StringBuilder sb = new StringBuilder(str1);
					String[] str = line.split(",,");

					sb.append(str[0] + ",");
					sb.append(str[1]);
					sb.append(str2);
					db.execSQL(sb.toString());
				}
				db.setTransactionSuccessful();
				db.endTransaction();
			}

			for (int i = 0; i < TB_NEWS.length; i++) {

				inputstream = new InputStreamReader(c.getAssets().open(
						TB_NEWS[i] + ".csv"));
				buffer = new BufferedReader(inputstream);
				line = "";

				columns = " day, title, desc, learning, effect, fluctuation, id ";
				str1 = "INSERT INTO " + TB_NEWS[i] + " (" + columns
						+ ") values(";
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
					sb.append(str[5] + ",");
					sb.append(id++);

					sb.append(str2);
					db.execSQL(sb.toString());

				}
				db.setTransactionSuccessful();
				db.endTransaction();

			}

			inputstream = new InputStreamReader(c.getAssets().open(
					TB_COMMON_NEWS + ".csv"));
			buffer = new BufferedReader(inputstream);
			line = "";

			columns = " day, company ,title, desc, learning, id ";
			str1 = "INSERT INTO " + TB_COMMON_NEWS + " (" + columns
					+ ") values(";
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
