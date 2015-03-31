package com.example.sample7customchoicelist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout implements Checkable {

	public ItemView(Context context) {
		super(context);
		init();
	}

	TextView titleView;
	ImageView checkView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		titleView = (TextView)findViewById(R.id.text_title);
		checkView = (ImageView)findViewById(R.id.image_check);
	}
	
	public void setText(String text) {
		titleView.setText(text);
	}

	private void drawCheck() {
		if (isChecked) {
			checkView.setImageResource(android.R.drawable.checkbox_on_background);
			setBackgroundColor(Color.GREEN);
		} else {
			checkView.setImageResource(android.R.drawable.checkbox_off_background);
			setBackgroundColor(Color.TRANSPARENT);
		}
	}
	boolean isChecked = false;
	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			drawCheck();
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void toggle() {
		setChecked(!isChecked);
	}
}
