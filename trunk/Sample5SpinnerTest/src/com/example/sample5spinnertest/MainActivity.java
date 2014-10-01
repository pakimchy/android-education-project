package com.example.sample5spinnertest;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	Spinner spinner;
//	ArrayAdapter<String> mAdapter;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spinner = (Spinner)findViewById(R.id.spinner1);
		
//		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
//		
//		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		mAdapter = new MyAdapter(this);
		
		spinner.setAdapter(mAdapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, spinner.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(MainActivity.this, "Not Selected", Toast.LENGTH_SHORT).show();
			}
		});
		
		initData();
	}
	
	private void initData() {
		String[] array = getResources().getStringArray(R.array.spinner_item);
		for(String str : array) {
			mAdapter.add(str);
		}
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
