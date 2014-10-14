package com.example.sample5database;

import java.util.ArrayList;

import android.content.ContentValues;
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
//		String sql = "insert into "+PersonTable.TABLE_NAME+"("+PersonTable.FIELD_NAME+","+PersonTable.FIELD_AGE+") values('" + p.name + "'," + p.age + ");";
//		db.execSQL(sql);

		ContentValues values = new ContentValues();
		values.put(PersonTable.FIELD_NAME, p.name);
		values.put(PersonTable.FIELD_AGE, p.age);
		db.insert(PersonTable.TABLE_NAME, null, values);
		
		db.close();
	}
	
	public void addPersonList(ArrayList<Person> list) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			for (Person p : list) {
				values.clear();
				values.put(PersonTable.FIELD_NAME, p.name);
				values.put(PersonTable.FIELD_AGE, p.age);
				db.insert(PersonTable.TABLE_NAME, null, values);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		
		db.close();
	}
	
	public void updatePerson(Person p) {
		if (p._id != -1) {
			SQLiteDatabase db = mHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(PersonTable.FIELD_NAME, p.name);
			values.put(PersonTable.FIELD_AGE, p.age);
			String selection = PersonTable._ID + " = ?";
			String[] selectionArgs = {"" + p._id};
			db.update(PersonTable.TABLE_NAME, values, selection, selectionArgs);
			db.close();
		} else {
			addPerson(p);
		}
	}

	public void deletePerson(Person p) {
		if (p._id != -1) {
			SQLiteDatabase db = mHelper.getWritableDatabase();
			String whereClause = PersonTable._ID + " = ?";
			String[] whereArgs = {"" + p._id};
			db.delete(PersonTable.TABLE_NAME, whereClause, whereArgs);
			db.close();
		}
	}
	
	
	public ArrayList<Person> getPersonList() {
		ArrayList<Person> list = new ArrayList<Person>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		
		String[] columns = {PersonTable._ID, PersonTable.FIELD_NAME, PersonTable.FIELD_AGE };
		String selection = PersonTable.FIELD_AGE + " > ? AND " + PersonTable.FIELD_AGE + " < ?";
		String[] selectionArgs = {"20","50"};
		
		Cursor c = db.query(PersonTable.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
		
		while(c.moveToNext()) {
			Person p = new Person();
			p._id = c.getLong(c.getColumnIndex(PersonTable._ID));
			p.name = c.getString(c.getColumnIndex(PersonTable.FIELD_NAME));
			p.age = c.getInt(c.getColumnIndex(PersonTable.FIELD_AGE));
			list.add(p);
		}
		c.close();
		
		return list;
	}
}
