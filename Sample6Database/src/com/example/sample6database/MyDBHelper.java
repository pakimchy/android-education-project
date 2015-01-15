package com.example.sample6database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "mydb";
	private static final int DB_VERSION = 1;
	
	public MyDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "+DBConstant.PersonTable.TABLE+"("
                + DBConstant.PersonTable._ID+" integer PRIMARY KEY autoincrement, " 
                + DBConstant.PersonTable.FIELD_NAME+" text, "
                + DBConstant.PersonTable.FIELD_AGE+" integer, "
                + DBConstant.PersonTable.FIELD_PHONE+" text);" ;
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
