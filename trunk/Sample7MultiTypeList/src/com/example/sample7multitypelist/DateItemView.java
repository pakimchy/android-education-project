package com.example.sample7multitypelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DateItemView extends FrameLayout {

	public DateItemView(Context context) {
		super(context);
		init();
	}
	TextView dateView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.date_layout, this);
		dateView = (TextView)findViewById(R.id.text_date);
	}
	
	public void setData(DateData data) {
		dateView.setText(data.date);
	}

}
