package com.example.sample7tabpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;


public class MainActivity extends ActionBarActivity {

	ViewPager pager;
	TabHost tabHost;
	TabsAdapter mAdapter;
	int currentTabIndex;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        pager = (ViewPager)findViewById(R.id.pager);
        mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);
        mAdapter.addTab(tabHost.newTabSpec("tab1").setIndicator("TAB1"), OneFragment.class, null);
        mAdapter.addTab(tabHost.newTabSpec("tab2").setIndicator("TAB2"), TwoFragment.class, null);
        mAdapter.addTab(tabHost.newTabSpec("tab3").setIndicator("TAB3"), ThreeFragment.class, null);
        
        mAdapter.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {				
				Toast.makeText(MainActivity.this, "tabId : " + tabId, Toast.LENGTH_SHORT).show();
			}
		});
        if (savedInstanceState != null) {
        	tabHost.setCurrentTab(savedInstanceState.getInt("tabIndex"));
        	mAdapter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putInt("tabIndex", tabHost.getCurrentTab());
    	mAdapter.onSaveInstanceState(outState);
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
