package com.example.sample6customchoice;

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
	String mData;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		titleView = (TextView)findViewById(R.id.text_title);
		checkView = (ImageView)findViewById(R.id.image_check);
		drawCheck();
	}
	
	public void setText(String text) {
		mData = text;
		titleView.setText(text);
	}
	
	boolean isChecked;

	private void drawCheck() {
		if (isChecked) {
			setBackgroundColor(Color.RED);
//			checkView.setImageResource(R.drawable.btn_check_on);
		} else {
			setBackgroundColor(Color.WHITE);
//			checkView.setImageResource(R.drawable.btn_check_off);
		}
	}
	
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
