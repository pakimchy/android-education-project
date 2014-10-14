package com.example.sample5database;

import com.example.sample5database.DBConstant.PersonTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "mydb";
	public static final int DB_VERSION = 1;
	
	public MyDBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "+ PersonTable.TABLE_NAME + "(" +
				PersonTable._ID + " integer PRIMARY KEY autoincrement," +
				PersonTable.FIELD_NAME + " text," +
				PersonTable.FIELD_AGE + " integer);";
		db.execSQL(sql);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
