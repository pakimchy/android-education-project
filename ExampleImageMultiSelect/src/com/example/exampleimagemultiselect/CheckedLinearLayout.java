package com.example.exampleimagemultiselect;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckedLinearLayout extends LinearLayout implements Checkable {

	boolean isChecked = false;
	
	public CheckedLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void drawChecked() {
		if (isChecked) {
			setBackgroundColor(Color.DKGRAY);
		} else {
			setBackgroundColor(Color.TRANSPARENT);
		}
	}

	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			drawChecked();
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
