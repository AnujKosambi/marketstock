package com.marketstock.sebiapplication.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "marketstock";

	private static final String TB_NEWS = "new";
	private static final String TB_INFOSYS = "infosys";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	private static final String CREATE_TB_NEWS = "CREATE TABLE "
			+ TB_NEWS
			+ "(id INTEGER PRIMARY KEY, title TEXT, desc TEXT, effect TEXT, learing TEXT, fluctuation TEXT)";

	private static final String CREATE_TB_INFOSYS = "CREATE TABLE "
			+ TB_INFOSYS
			+ "(id INTEGER PRIMARY KEY, date TEXT, openPrice REAL, closePrice REAL, highPrice REAL, lowPrice REAL, volume REAL)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TB_NEWS);
		db.execSQL(CREATE_TB_INFOSYS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TB_NEWS);
		db.execSQL("DROP TABLE IF EXISTS " + TB_INFOSYS);

		onCreate(db);

	}

}
