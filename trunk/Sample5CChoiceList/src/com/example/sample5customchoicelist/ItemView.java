package com.example.sample5customchoicelist;

import android.content.Context;
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
		titleView = (TextView)findViewById(R.id.title);
		checkView = (ImageView)findViewById(R.id.item_check);
	}
	
	public void setText(String text) {
		titleView.setText(text);
	}

	boolean isChecked = false;
	
	private void drawCheck() {
		if (isChecked) {
			checkView.setImageResource(R.drawable.btn_check_on);
		} else {
			checkView.setImageResource(R.drawable.btn_check_off);
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
