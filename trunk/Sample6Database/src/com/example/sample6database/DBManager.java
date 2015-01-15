package com.example.sample6database;

import java.util.ArrayList;

import com.example.sample6database.DBConstant.PersonTable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager implements PersonTable {
	private static DBManager instance;

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	MyDBHelper mHelper;

	private DBManager() {
		mHelper = new MyDBHelper(MyApplication.getContext());
	}

	public ArrayList<Person> getPersonList() {
		ArrayList<Person> list = new ArrayList<Person>();
		String sql = String.format("SELECT %s, %s, %s FROM %s",
				PersonTable._ID, PersonTable.FIELD_NAME, PersonTable.FIELD_AGE,
				PersonTable.TABLE);
		String[] columns = {_ID, FIELD_NAME, FIELD_AGE};		
		SQLiteDatabase db = mHelper.getReadableDatabase();
//		Cursor c = db.rawQuery(sql, null);
		Cursor c = db.query(TABLE, columns, null,null, null, null, null);
		while (c.moveToNext()) {
			Person p = new Person();
			p.name = c.getString(c.getColumnIndex("name"));
			p.age = c.getInt(c.getColumnIndex("age"));
			list.add(p);
		}
		c.close();
		return list;
	}

	public void addPerson(Person p) {
		String sql = String.format("INSERT INTO %s(%s,%s) values(?,?);",
				PersonTable.TABLE, PersonTable.FIELD_NAME,
				PersonTable.FIELD_AGE);
		String[] args = { p.name, "" + p.age };
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIELD_NAME, p.name);
		values.put(FIELD_AGE, p.age);
		db.insert(TABLE, null, values);
//		db.execSQL(sql, args);
		db.close();
	}

	public Cursor getPersonCursor() {
		String[] columns = {_ID, FIELD_NAME, FIELD_AGE};		
		SQLiteDatabase db = mHelper.getReadableDatabase();
		String selection = FIELD_AGE + " > ? AND " + FIELD_AGE + " < ?";
		String[] args = {"20","40"};
		Cursor c = db.query(TABLE, columns, selection,args, null, null, null);
		return c;
	}

}
