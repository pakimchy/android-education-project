package com.example.sampletabheadersectionlist;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TabWidget;

public class MainActivity extends ActionBarActivity {

	FragmentTabHost tabHost;
	TabWidget tabWidget;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
		tabWidget = (TabWidget)findViewById(android.R.id.tabs);
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec(Constants.TAB_IDS[0]).setIndicator("TAB1"), FragmentOne.class, null);
		tabHost.addTab(tabHost.newTabSpec(Constants.TAB_IDS[1]).setIndicator("TAB2"), FragmentTwo.class, null);
		tabHost.addTab(tabHost.newTabSpec(Constants.TAB_IDS[2]).setIndicator("TAB3"), FragmentThree.class, null);
	}
	
	public void setTabCurrent(String tag) {
		tabHost.setCurrentTabByTag(tag);
	}
	
	public void setTabWidgetVisible(boolean isVisible) {
		if (isVisible) {
			tabWidget.setVisibility(View.VISIBLE);
		} else {
			tabWidget.setVisibility(View.GONE);
		}
	}
	
}
