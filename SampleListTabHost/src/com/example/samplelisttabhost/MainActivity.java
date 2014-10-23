package com.example.samplelisttabhost;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		View headerView = getLayoutInflater().inflate(R.layout.header_layout, listView, false);
		tabHost = (TabHost)headerView.findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("TAB1").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("TAB2").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("TAB3").setContent(R.id.tab1));
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("tab1")) {
					initData("tab1");
				} else if (tabId.equals("tab2")) {
					initData("tab2");
				} else if (tabId.equals("tab3")) {
					initData("tab3");
				}
			}
		});
		
		listView.addHeaderView(headerView);
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>());
		listView.setAdapter(mAdapter);
		
		initData("tab1");
	}
	
	private void initData(String tabname) {
		mAdapter.clear();
		for (int i = 0; i < 20; i++) {
			mAdapter.add(tabname + " : " + i);
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
