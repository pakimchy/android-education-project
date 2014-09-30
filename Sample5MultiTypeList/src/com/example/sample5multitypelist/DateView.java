package com.example.sample5multitypelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DateView extends FrameLayout {

	public DateView(Context context) {
		super(context);
		init();
	}

	TextView dateView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.date_layout, this);
		dateView = (TextView)findViewById(R.id.date);
	}
	
	public void setText(String text) {
		dateView.setText(text);
	}
	
}
