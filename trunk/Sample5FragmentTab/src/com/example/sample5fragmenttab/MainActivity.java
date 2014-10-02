package com.example.sample5fragmenttab;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends ActionBarActivity {

	private static final String TAB1_ID = "tab1";
	private static final String TAB2_ID = "tab2";
	private static final String TAB3_ID = "tab3";
	FragmentTabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec(TAB1_ID).setIndicator("TAB1"), FragmentOne.class, null);
		tabHost.addTab(tabHost.newTabSpec(TAB2_ID).setIndicator("TAB2"), FragmentTwo.class, null);
		tabHost.addTab(tabHost.newTabSpec(TAB3_ID).setIndicator("TAB3"), FragmentThree.class, null);
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals(TAB1_ID)) {
					
				} else if (tabId.equals(TAB2_ID)) {
					
				} else if (tabId.equals(TAB3_ID)) {
					
				}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		if (!tabHost.getCurrentTabTag().equals(TAB1_ID)) {
			tabHost.setCurrentTabByTag(TAB1_ID);
		} else {
			super.onBackPressed();
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
