package com.example.testtabpagertest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	FragmentTabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (FragmentTabHost)findViewById(R.id.tabHost);
		tabHost.setup(this,getFragmentManager(), R.id.realtabcontent);
		
		tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator("TAB1"), MainFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator("TAB2"), TwoFragment.class, null);
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
