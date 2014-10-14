package com.example.sample5database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sample5database.DBConstant.PersonTable;

public class MainActivity extends ActionBarActivity {

	ListView listView;
//	ArrayAdapter<Person> mAdapter;
	
	SimpleCursorAdapter mAdapter;
	
	int ageColumnIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
//		mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, new ArrayList<Person>());
		String[] from = {PersonTable.FIELD_NAME , PersonTable.FIELD_AGE};
		int[] to = {R.id.name, R.id.age};
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == ageColumnIndex) {
					TextView tv = (TextView)view;
					String text = cursor.getString(columnIndex);
					tv.setText("age : " + text);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}
	
	Cursor mCursor;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCursor != null) {
			mCursor.close();
		}
	}
	
	private void initData() {
		mCursor = DBManager.getInstance().getPersonCursor();
		ageColumnIndex = mCursor.getColumnIndex(PersonTable.FIELD_AGE);
		mAdapter.swapCursor(mCursor);
//		mAdapter.clear();
//		ArrayList<Person> list = DBManager.getInstance().getPersonList();
//		for (Person p : list) {
//			mAdapter.add(p);
//		}
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
		if (id == R.id.menu_add) {
			Intent i = new Intent(this, DBAddActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
