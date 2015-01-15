package com.example.sample6database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<Person> mAdapter;
	SimpleCursorAdapter mCursorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1);
		String[] from = {DBConstant.PersonTable.FIELD_NAME, DBConstant.PersonTable.FIELD_AGE};
		int[] to = {R.id.text_name, R.id.text_age};
		mCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, null, from, to, 0);
//		listView.setAdapter(mAdapter);
		listView.setAdapter(mCursorAdapter);
		
		Button btn = (Button)findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, DataAddActivity.class);
				startActivity(i);
			}
		});
		
	}
	
	private void initData() {
//		ArrayList<Person> list = DBManager.getInstance().getPersonList();
//		mAdapter.clear();
//		for (Person p : list) {
//			mAdapter.add(p);
//		}
		Cursor c = DBManager.getInstance().getPersonCursor();
		mCursorAdapter.changeCursor(c);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCursorAdapter.changeCursor(null);
	}
	@Override
	protected void onResume() {
		super.onResume();
		initData();
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
