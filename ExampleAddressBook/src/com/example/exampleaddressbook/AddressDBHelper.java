package com.example.exampleaddressbook;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.exampleaddressbook.AddressBookConstant.AddressTable;

public class AddressDBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "addressbook";
	private static final int DB_VERSION = 1;

	private static AddressDBHelper instance;

	public static AddressDBHelper getInstance() {
		if (instance == null) {
			instance = new AddressDBHelper();
		}
		return instance;
	}

	private AddressDBHelper() {
		super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + AddressTable.TABLE + "(" 
	            + AddressTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ AddressTable.NAME + " TEXT NOT NULL," 
	            + AddressTable.PHONE + " TEXT,"
				+ AddressTable.HOME + " TEXT," 
	            + AddressTable.OFFICE + " TEXT);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// not implements...
	}

	public long insertAddressBook(Address address) {
		if (address.id > 0) {
			updateAddressBook(address);
			return address.id;
		}
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(AddressTable.NAME, address.name);
		values.put(AddressTable.PHONE, address.phone);
		values.put(AddressTable.HOME, address.home);
		values.put(AddressTable.OFFICE, address.office);
		long id = db.insert(AddressTable.TABLE, null, values);
		db.close();
		return id;
	}

	public void updateAddressBook(Address address) {
		if (address.id == 0) {
			address.id = insertAddressBook(address);
			return;
		}
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = AddressTable._ID + " = ?";
		String[] whereArgs = new String[] { "" + address.id };
		ContentValues values = new ContentValues();
		values.put(AddressTable.NAME, address.name);
		values.put(AddressTable.PHONE, address.phone);
		values.put(AddressTable.HOME, address.home);
		values.put(AddressTable.OFFICE, address.office);
		db.update(AddressTable.TABLE, values, whereClause, whereArgs);
		db.close();
	}

	public void deleteAddressBook(Address address) {
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = AddressTable._ID + " = ?";
		String[] whereArgs = new String[] { "" + address.id };
		db.delete(AddressTable.TABLE, whereClause, whereArgs);
		db.close();
	}

	public Cursor searchAddressBookCursor(String keyword) {
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = new String[] { AddressTable._ID, AddressTable.NAME,
				AddressTable.PHONE, AddressTable.HOME, AddressTable.OFFICE };
		String selection = null;
		String[] selectionArgs = null;
		if (keyword != null && !keyword.equals("")) {
			selection = AddressTable.NAME + " LIKE ? OR " + AddressTable.PHONE + " LIKE ? OR " +
				        AddressTable.HOME + " LIKE ? OR " + AddressTable.OFFICE + " LIKE ?";
			selectionArgs = new String[] { "%" + keyword + "%" , "%" + keyword + "%" , "%" + keyword + "%" , "%" + keyword + "%"};
		}
		String orderBy = AddressTable.NAME + " COLLATE LOCALIZED ASC";
		return db.query(AddressTable.TABLE, columns, selection, selectionArgs, null, null, orderBy);		
	}
	
	public List<Address> searchAddressBook(String keyword) {
		List<Address> list = new ArrayList<Address>();
		Cursor c = searchAddressBookCursor(keyword);
		while (c.moveToNext()) {
			Address addr = new Address();
			addr.id = c.getLong(c.getColumnIndex(AddressTable._ID));
			addr.name = c.getString(c.getColumnIndex(AddressTable.NAME));
			addr.phone = c.getString(c.getColumnIndex(AddressTable.PHONE));
			addr.home = c.getString(c.getColumnIndex(AddressTable.HOME));
			addr.office = c.getString(c.getColumnIndex(AddressTable.OFFICE));
			list.add(addr);
		}
		c.close();
		return list;
	}
}
