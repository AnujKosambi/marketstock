package com.marketstock.sebiapplication.dbhelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	Context c;

	private static final String DB_NAME = "marketstock.db";

	private static final String TB_NEWS = "new";
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
			+ " (id INTEGER PRIMARY KEY, title TEXT, desc TEXT, effect TEXT, learing TEXT, fluctuation TEXT)";

	private static final String CREATE_TB_INFOSYS = "CREATE TABLE "
			+ TB_INFOSYS
			+ " (id INTEGER PRIMARY KEY, date TEXT, openPrice REAL, closePrice REAL, highPrice REAL, lowPrice REAL, volume REAL)";

	private BufferedReader buffer;

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TB_NEWS);
		db.execSQL(CREATE_TB_INFOSYS);

		InputStreamReader inputstream;
		try {

			inputstream = new InputStreamReader(c.getAssets().open(
					"infosys.csv"));
			buffer = new BufferedReader(inputstream);
			String line = "";
			String tableName = "infosys";
			String columns = " date, openPrice, highPrice, lowPrice, closePrice,volume,id ";
			String str1 = "INSERT INTO " + tableName + " (" + columns
					+ ") values(";
			String str2 = ");";

			db.beginTransaction();
			while ((line = buffer.readLine()) != null) {
				StringBuilder sb = new StringBuilder(str1);
				String[] str = line.split(",");
				SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
				//date = new Date(format.parse(cursor.getString(1)).getTime());
				 Log.d("Date", str[0]);
				sb.append("'" +  str[0] + "',");
				sb.append(str[1] + ",");
				sb.append(str[2] + ",");
				sb.append(str[3] + ",");
				sb.append(str[4] + ",");
				sb.append(str[5] + ",");
				sb.append(str[6]);

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
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TB_NEWS);
		db.execSQL("DROP TABLE IF EXISTS " + TB_INFOSYS);

		onCreate(db);

	}

}
