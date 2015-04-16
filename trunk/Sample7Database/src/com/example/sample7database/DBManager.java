package com.example.sample7database;

import java.util.ArrayList;
import java.util.List;

import com.example.sample7database.DBConstant.AddressTable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper implements AddressTable {

	private static final String DB_NAME = "address";
	private static final int DB_VERSION = 1;
	
	private static DBManager instance;
	public static DBManager getInstance() {
		if(instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
		super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "+TABLE+" ("+
	                 _ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
	                 NAME+" TEXT NOT NULL," +
	                 EMAIL+" TEXT," +
	                 PHONE+" TEXT," +
	                 ADDRESS+" TEXT);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public List<ItemData> getList(String keyword) {
		List<ItemData> list = new ArrayList<ItemData>();
		String[] columns = {_ID, NAME, EMAIL, PHONE ,ADDRESS};
		String selection = null;
		String[] selectionArgs = null;
		String orderBy = NAME+" ASC";
		if (keyword != null && !keyword.equals("")) {
			selection = NAME+" LIKE ? AND "+ADDRESS+" LIKE ?";
			selectionArgs = new String[] {"%"+keyword + "%","%"+keyword + "%"};
		}
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(TABLE, columns, selection, selectionArgs, null, null, orderBy);
		while(c.moveToNext()) {
			ItemData d = new ItemData();
			d.id = c.getLong(c.getColumnIndex(_ID));
			d.name = c.getString(c.getColumnIndex(NAME));
			d.email = c.getString(c.getColumnIndex(EMAIL));
			d.phone = c.getString(c.getColumnIndex(PHONE));
			d.address = c.getString(c.getColumnIndex(ADDRESS));
			list.add(d);
		}
		c.close();
		return list;
	}

	public long addItem(ItemData item) {
		if (item.id == -1) {
			ContentValues values = new ContentValues();
			values.put(NAME, item.name);
			values.put(EMAIL, item.email);
			values.put(PHONE, item.phone);
			values.put(ADDRESS, item.address);
			SQLiteDatabase db = getWritableDatabase();
			return db.insert(TABLE, null, values);
		} else {
			updateItem(item);
		}
		return -1;
	}
	
	public void updateItem(ItemData item) {
		if (item.id == -1) {
			addItem(item);
			return;
		}
		ContentValues values = new ContentValues();
		values.put(NAME, item.name);
		values.put(EMAIL, item.email);
		values.put(PHONE, item.phone);
		values.put(ADDRESS, item.address);
		SQLiteDatabase db = getWritableDatabase();
		String where = _ID+" = ?";
		String[] whereArgs = {"" + item.id};
		db.update(TABLE, values, where, whereArgs);
	}
	
	public void deleteItem(ItemData item) {
		if (item.id == -1) {
			return;
		}
		SQLiteDatabase db = getWritableDatabase();
		String where = _ID+" = ?";
		String[] whereArgs = {"" + item.id};
		db.delete(TABLE, where, whereArgs);
	}
}
