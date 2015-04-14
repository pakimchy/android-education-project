package com.example.exampledatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	private static final String DB_NAME = "mydb";
	private static final int DB_VERSION = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btn_open);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openDatabase();
			}
		});
	}

	private void openDatabase() {
		SQLiteDatabase db = openOrCreateDatabase(DB_NAME, 0, null);
		int version = db.getVersion();
		try {
			db.beginTransaction();
			if (version == 0) {
				createTable(db);
			} else if (version != DB_VERSION) {
				changeDB(db);
			}
			db.setVersion(DB_VERSION);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	private void createTable(SQLiteDatabase db) {
		String sql = "CREATE TABLE persontable(" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				    "name TEXT NOT NULL," +
					"age INTEGER" +
					")";
		db.execSQL(sql);
	}

	private void insertName(SQLiteDatabase db, String name, int age) {
		String sql = "INSERT INTO persontable(name) values('"+name+"',"+age+")";
		db.execSQL(sql);
	}
	
	private void insertNameWithArguemnt(SQLiteDatabase db, String name, int age) {
		String sql = "INSERT INTO persontable(name) values(?,?)";
		Object[] bindArgs = new Object[] {name, age};
		db.execSQL(sql, bindArgs);
	}
	
	private Cursor selectName(SQLiteDatabase db, int min, int max) {
		String sql = "SELECT _id, name, age FROM persontable WHERE age BETWEEN ? AND ?";
		String[] selectionArgs = new String[] {""+min, ""+max};
		Cursor c = db.rawQuery(sql, selectionArgs);
		return c;
	}
	
	private void insertPerson(String name, int age) {
		PersonDBHelper helper = new PersonDBHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.clear();
		values.put("name", name);
		values.put("age", age);
		db.insert("persontable", null, values);
		db.close();
	}
	
	private void insertPerson(SQLiteDatabase db, String name, int age) {
		ContentValues values = new ContentValues();
		values.clear();
		values.put("name", name);
		values.put("age", age);
		db.insert("persontable", null, values);
	}
	
	private void updatePerson(SQLiteDatabase db, String name, int age, long id) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("age", age);
		String whereClause = "_id = ?";
		String[] whereArgs = new String[] { "" + id };
		db.update("persontable", values, whereClause, whereArgs);
	}
	private void selectPerson(SQLiteDatabase db, String name) {
		String[] columns = new String[]{"_id", "name" , "age"};
		String selection = "name = ?";
		String[] selectionArgs = new String[]{name};
		String orderBy = "age ASC";
		Cursor c = db.query("persontable", columns, selection, selectionArgs, null, null, orderBy);
	}
	
	private void selectPersonCount(int min, int max) {
		PersonDBHelper helper = new PersonDBHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = new String[]{"age", "count(_id) AS cnt"};
		String selection = "age BETWEEN ? AND ?";
		String[] selectionArgs = new String[]{"" + min, "" + max};
		String groupBy = "age";
		String having = "cnt > 10";
		String orderBy = "age ASC";
		String limit = "10, 1";
		Cursor c = db.query("persontable", columns, selection, selectionArgs, groupBy, having, orderBy, limit);		
	}
	
	private void selectPersonCount(SQLiteDatabase db, int min, int max) {
		String[] columns = new String[]{"age", "count(_id) AS cnt"};
		String selection = "age BETWEEN ? AND ?";
		String[] selectionArgs = new String[]{"" + min, "" + max};
		String groupBy = "age";
		String having = "cnt > 10";
		String orderBy = "age ASC";
		String limit = "10, 1";
		Cursor c = db.query("persontable", columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
	
	private void deletePerson(SQLiteDatabase db, String name) {
		String whereClause = "name = ?";
		String[] whereArgs = new String[] { name };
		db.delete("persontable", whereClause, whereArgs);
	}
	private void printPersonTable(SQLiteDatabase db) {
		String sql = "SELECT _id, name, age FROM persontable";
		Cursor c = db.rawQuery(sql, null);
		int idIndex = c.getColumnIndex("_id");
		int nameIndex = c.getColumnIndex("name");
		int ageIndex = c.getColumnIndex("age");
		while(c.moveToNext()) {
			long id = c.getLong(idIndex);
			String name = c.getString(nameIndex);
			int age = c.getInt(ageIndex);
		}
		c.close();
	}
	
	private void changeDB(SQLiteDatabase db) {
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
