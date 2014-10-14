package com.example.sample5database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sample5database.DBConstant.PersonTable;

public class DBManager {
	
	private static DBManager instance;
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	MyDBOpenHelper mHelper;

	private DBManager() {
		mHelper = new MyDBOpenHelper(MyApplication.getContext());
	}
	
	public void addPerson(Person p) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String sql = "insert into "+PersonTable.TABLE_NAME+"("+PersonTable.FIELD_NAME+","+PersonTable.FIELD_AGE+") values('" + p.name + "'," + p.age + ");";
		db.execSQL(sql);
		db.close();
	}
	
	public ArrayList<Person> getPersonList() {
		ArrayList<Person> list = new ArrayList<Person>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String sql = "SELECT "+PersonTable.FIELD_NAME+", "+PersonTable.FIELD_AGE+" FROM " + PersonTable.TABLE_NAME;
		
		Cursor c = db.rawQuery(sql, null);
		while(c.moveToNext()) {
			Person p = new Person();
			p.name = c.getString(c.getColumnIndex(PersonTable.FIELD_NAME));
			p.age = c.getInt(c.getColumnIndex(PersonTable.FIELD_AGE));
			list.add(p);
		}
		
		
		return list;
	}
}
