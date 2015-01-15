package com.example.sample6imagemultiselect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ItemView extends FrameLayout implements Checkable {

	public ItemView(Context context) {
		super(context);
		init();
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	ImageView checkView;

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		checkView = (ImageView) findViewById(R.id.image_check);
	}

	private void drawCheck() {
		if (isChecked) {
			checkView
					.setImageResource(android.R.drawable.checkbox_on_background);
		} else {
			checkView
					.setImageResource(android.R.drawable.checkbox_off_background);
		}
	}

	boolean isChecked;

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
