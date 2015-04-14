package com.example.exampledatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "persondb";
	private static final int DB_VERSION = 1;

	public PersonDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE persontable("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT NOT NULL," + "age INTEGER" + ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
