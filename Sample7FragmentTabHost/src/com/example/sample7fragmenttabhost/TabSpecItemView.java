package com.example.sample7fragmenttabhost;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TabSpecItemView extends FrameLayout {

	public TabSpecItemView(Context context) {
		super(context);
		init();
	}
	
	TextView nameView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.tabspec_item_layout, this);
		nameView = (TextView)findViewById(R.id.text_name);
		setBackgroundResource(R.drawable.tabspec_background);
	}

	public void setText(String name) {
		nameView.setText(name);
	}
}
