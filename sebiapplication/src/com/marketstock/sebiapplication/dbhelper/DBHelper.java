package com.marketstock.sebiapplication.dbhelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	Context c;

	private static final String DB_NAME = "marketstock.db";

	private static final String TB_NEWS = "infosysnew";
	private static final String TB_INFOSYS = "infosys";

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

	private static final String CREATE_TB_INFOSYS = "CREATE TABLE "
			+ TB_INFOSYS
			+ " (id INTEGER PRIMARY KEY, date TEXT, openPrice REAL, closePrice REAL, highPrice REAL, lowPrice REAL, volume REAL)";

	private BufferedReader buffer;

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TB_NEWS);
		db.execSQL(CREATE_TB_INFOSYS);

		InputStreamReader inputstream;
		String line, tableName, columns, str1, str2;
		try {

			inputstream = new InputStreamReader(c.getAssets().open(
					"infosys.csv"));
			buffer = new BufferedReader(inputstream);
			line = "";
			tableName = "infosys";
			columns = " date, openPrice, highPrice, lowPrice, closePrice,volume,id ";
			str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
			str2 = ");";

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
				sb.append(str[6]);

				sb.append(str2);
				db.execSQL(sb.toString());
				
				Log.d("Q1","Q1 Running");
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			Log.d("New","Q1 Completed");
				
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
				Log.d("few",sb.toString());
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
		db.execSQL("DROP TABLE IF EXISTS " + TB_INFOSYS);

		onCreate(db);

	}

}
