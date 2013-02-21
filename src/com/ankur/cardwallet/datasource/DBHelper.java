package com.ankur.cardwallet.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_CARD_INFO = "card_info";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CARDNUM = "card_number";
	public static final String COLUMN_CVV = "cvv";
	public static final String COLUMN_EXPDATE = "exp_date";
	public static final String COLUMN_BILLINGZIP = "billing_zip";
	public static final String COLUMN_ALIAS = "alias";
	
	private static final String DATABASE_NAME = "card_info.db";
	private static final int DATABASE_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_CARD_INFO + "( " + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME
			+ " text not null, "+ COLUMN_ALIAS
			+ " text not null, "+COLUMN_CARDNUM
			+ " text not null, "+COLUMN_CVV
			+ " text not null, "+COLUMN_EXPDATE
			+ " text not null, "+COLUMN_BILLINGZIP+" );";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD_INFO);
		onCreate(db);
		
	}

}
