package com.example.sampletabheadersectionlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class ItemTabWidget extends FrameLayout {

	public ItemTabWidget(Context context) {
		super(context);
		init();
	}

	OnTabChangeListener mListener;
	public void setOnTabChangeListener(OnTabChangeListener listener) {
		mListener = listener;
	}
	
	TabHost tabHost;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_tabwidget, this);
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec(Constants.TAB_IDS[0]).setIndicator("TAB1").setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec(Constants.TAB_IDS[1]).setIndicator("TAB2").setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec(Constants.TAB_IDS[2]).setIndicator("TAB3").setContent(R.id.tab3));
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				String tag = Constants.TAB_IDS[currentIndex];
				if (!tag.equals(tabId) && mListener != null) {
					mListener.onTabChanged(tabId);
				}
			}
		});
	}
	
	private int currentIndex;
	public void setCurrentTab(int index) {
		currentIndex = index;
		tabHost.setCurrentTab(index);
	}
}
