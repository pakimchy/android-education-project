package com.example.exampleactionbar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	TextView titleView;
	ArrayAdapter<String> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getSupportActionBar();
				
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setCustomView(R.layout.custom_action);
//		titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_action);
//		titleView.setText("ActionBar Custom");
		
//		actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);
//		
//		actionBar.setDisplayShowHomeEnabled(true);
//		actionBar.setIcon(R.drawable.ic_launcher);
//		
//		actionBar.setDisplayUseLogoEnabled(true);
//		actionBar.setLogo(R.drawable.ic_launcher);
//		
//		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setSubtitle("subtitle");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		actionBar.setListNavigationCallbacks(mAdapter, new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int position, long id) {
				String item = mAdapter.getItem(position);
				Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		initData();
	}
	
	private void initData() {
		mAdapter.add("Item 1");
		mAdapter.add("Item 2");
		mAdapter.add("Item 3");
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
		if (id == android.R.id.home) {
			return true;
		}
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
