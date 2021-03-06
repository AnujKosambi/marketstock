package com.marketstock.sebiapplication.dbhelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	Context c;

	public static final String DB_NAME = "marketstock.db";

	public static final String TB_NEWS[] = { "infosysnews", "bajajnews",
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
	private static String DB_PATH = "";
	public static final int level[][] = { { 1, 14, 28 }, { 0, 15, 16 },
			{ 21, 25, 8 }, { 9, 17, 18 }, { 3, 4, 5 }, { 6, 7, 10 },
			{ 11, 12, 13 }, { 19, 20, 22 }, { 23, 24, 26 }, { 27, 29, 2 } };

	private static final float wallet = 200000f;

	public DBHelper(Context context) throws IOException {
		super(context, DB_NAME, null, 1);

		if (android.os.Build.VERSION.SDK_INT >= 17) {
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		} else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		c = context;
		createDataBase();
	}

	public void createDataBase() throws IOException {
		// If database not exists copy it from the assets

		boolean mDataBaseExist = checkDataBase();
		if (!mDataBaseExist) {
			this.getReadableDatabase();
			this.close();
			try {
				// Copy the database from assests
				copyDataBase();
				Log.e("log", "createDatabase database created");
			} catch (IOException mIOException) {
				throw new Error("ErrorCopyingDataBase");
			}
		}
	}

	private boolean checkDataBase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		// Log.v("dbFile", dbFile + "   "+ dbFile.exists());
		return dbFile.exists();
	}
	
	   //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        InputStream mInput = c.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
//
//	private static final String CREATE_TB_UD = "CREATE TABLE "
//			+ TB_USERDATA
//			+ " (company TEXT, holdings TEXT, avg_price TEXT, amount TEXT, profit TEXT)";
//
//	private static final String CREATE_TB_CD = "CREATE TABLE " + TB_COMPANYDATA
//			+ " (company TEXT,price REAL,weight REAL,percentChange REAL)";
//
//	private static final String CREATE_TB_CN = "CREATE TABLE "
//			+ TB_COMMON_NEWS
//			+ " (id INTEGER PRIMARY KEY,company TEXT, title TEXT, desc TEXT, learning TEXT, day INTEGER)";
//
//	private static final String CREATE_TB_TIPS = "CREATE TABLE " + TB_TIPS
//			+ " (id INTEGER PRIMARY KEY, tips TEXT)";
//
//	private static final String CREATE_TB_DF = "CREATE TABLE " + TB_DF
//			+ " (id INTEGER PRIMARY KEY,term TEXT,definition TEXT)";

//	private BufferedReader buffer;

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.setMaximumSize(10240 * 1024);

		SharedPreferences prefs = c.getSharedPreferences(
				"com.marketstock.sebiapplication", Context.MODE_PRIVATE);
		prefs.edit()
				.putLong("Date", Calendar.getInstance().getTime().getTime())
				.commit();
		prefs.edit().putFloat("psensex", 15000.0f).commit();
		prefs.edit().putFloat("sensex", 15000.0f).commit();

		SharedPreferences settings = c
				.getSharedPreferences(
						"com.marketstock.sebiapplication.setting",
						Context.MODE_PRIVATE);

		settings.edit().putFloat("wallet", wallet).commit();
		settings.edit().putFloat("networth", wallet).commit();
		settings.edit().putInt("level", 1).commit();
		
//		db.execSQL(CREATE_TB_UD);
//		db.execSQL(CREATE_TB_CD);
//		db.execSQL(CREATE_TB_CN);
//		db.execSQL(CREATE_TB_TIPS);
//		db.execSQL(CREATE_TB_DF);
//		InputStreamReader inputstream;
//		String line, tableName, columns, str1, str2;
//
//		for (int i = 0; i < TB_STOCKS.length; i++) {
//			String CREATE_TB_INFOSYS = "CREATE TABLE "
//					+ TB_STOCKS[i]
//					+ " (id INTEGER PRIMARY KEY, date TEXT, openPrice REAL, closePrice REAL, highPrice REAL, lowPrice REAL, volume REAL)";
//
//			db.execSQL(CREATE_TB_INFOSYS);
//
//			str1 = "INSERT INTO " + TB_COMPANYDATA
//					+ " (company, price, weight,percentChange" + ") values('";
//			str2 = "',0,0,0);";
//
//			db.execSQL(str1 + TB_STOCKS[i] + str2);
//
//		}
//
//		for (int i = 0; i < TB_NEWS.length; i++) {
//
//			String CREATE_TB_NEWS = "CREATE TABLE "
//					+ TB_NEWS[i]
//					+ " (id INTEGER PRIMARY KEY, title TEXT, desc TEXT, effect TEXT, learning TEXT, fluctuation TEXT,day INTEGER)";
//
//			db.execSQL(CREATE_TB_NEWS);
//
//		}
//
//		try {
//
//			for (int i = 0; i < TB_STOCKS.length; i++) {
//
//				inputstream = new InputStreamReader(c.getAssets().open(
//						TB_STOCKS[i] + ".csv"));
//				buffer = new BufferedReader(inputstream);
//				line = "";
//				tableName = TB_STOCKS[i];
//				columns = " date, openPrice, highPrice, lowPrice, closePrice,volume,id ";
//				str1 = "INSERT INTO " + tableName + " (" + columns
//						+ ") values(";
//				str2 = ");";
//				int j = 1;
//				db.beginTransaction();
//				while ((line = buffer.readLine()) != null) {
//					StringBuilder sb = new StringBuilder(str1);
//					String[] str = line.split(",");
//					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//
//					sb.append("'" + format.parse(str[0]).getTime() + "',");
//					sb.append(str[1] + ",");
//					sb.append(str[2] + ",");
//					sb.append(str[3] + ",");
//					sb.append(str[4] + ",");
//					sb.append(str[5] + ",");
//					sb.append(j++);
//
//					sb.append(str2);
//					db.execSQL(sb.toString());
//
//				}
//				db.setTransactionSuccessful();
//				db.endTransaction();
//
//			}
//
//			String table;
//			for (int i = 0; i < 2; i++) {
//				int j = 1;
//				if (i == 0) {
//					table = TB_TIPS;
//					columns = " id,tips ";
//				} else {
//					table = TB_DF;
//					columns = " term,definition,id ";
//				}
//
//				inputstream = new InputStreamReader(c.getAssets().open(
//						table + ".csv"));
//
//				buffer = new BufferedReader(inputstream);
//				line = "";
//
//				str1 = "INSERT INTO " + table + " (" + columns + ") values(";
//				str2 = ");";
//
//				db.beginTransaction();
//
//				while ((line = buffer.readLine()) != null) {
//
//					StringBuilder sb = new StringBuilder(str1);
//					String[] str = line.split(",,");
//
//					sb.append(str[0] + ",");
//					sb.append(str[1]);
//					if (i == 1) {
//						sb.append("," + j++);
//					}
//					sb.append(str2);
//					db.execSQL(sb.toString());
//				}
//				db.setTransactionSuccessful();
//				db.endTransaction();
//			}
//
//			for (int i = 0; i < TB_NEWS.length; i++) {
//
//				inputstream = new InputStreamReader(c.getAssets().open(
//						TB_NEWS[i] + ".csv"));
//				buffer = new BufferedReader(inputstream);
//				line = "";
//
//				columns = " day, title, desc, learning, effect, fluctuation, id ";
//				str1 = "INSERT INTO " + TB_NEWS[i] + " (" + columns
//						+ ") values(";
//				str2 = ");";
//
//				db.beginTransaction();
//				int id = 1;
//				while ((line = buffer.readLine()) != null) {
//
//					StringBuilder sb = new StringBuilder(str1);
//					String[] str = line.split(",,");
//
//					sb.append(str[0] + ",");
//					sb.append(str[1] + ",");
//					sb.append(str[2] + ",");
//					sb.append(str[3] + ",");
//					sb.append(str[4] + ",");
//					sb.append(str[5] + ",");
//					sb.append(id++);
//
//					sb.append(str2);
//					db.execSQL(sb.toString());
//
//				}
//				db.setTransactionSuccessful();
//				db.endTransaction();
//
//			}
//
//			inputstream = new InputStreamReader(c.getAssets().open(
//					TB_COMMON_NEWS + ".csv"));
//			buffer = new BufferedReader(inputstream);
//			line = "";
//
//			columns = " day, company ,title, desc, learning, id ";
//			str1 = "INSERT INTO " + TB_COMMON_NEWS + " (" + columns
//					+ ") values(";
//			str2 = ");";
//
//			db.beginTransaction();
//			int id = 1;
//			while ((line = buffer.readLine()) != null) {
//
//				StringBuilder sb = new StringBuilder(str1);
//				String[] str = line.split(",,");
//
//				sb.append(str[0] + ",");
//				sb.append(str[1] + ",");
//				sb.append(str[2] + ",");
//				sb.append(str[3] + ",");
//				sb.append(str[4] + ",");
//				sb.append(id++);
//
//				sb.append(str2);
//				db.execSQL(sb.toString());
//
//			}
//			db.setTransactionSuccessful();
//			db.endTransaction();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

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
