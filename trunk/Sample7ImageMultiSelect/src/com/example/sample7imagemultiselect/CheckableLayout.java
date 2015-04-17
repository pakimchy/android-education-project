package com.example.sample7imagemultiselect;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CheckableLayout extends FrameLayout implements Checkable {

	public CheckableLayout(Context context) {
		super(context);
	}

	public CheckableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	ImageView checkView;
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		checkView = new ImageView(getContext());
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.BOTTOM);
		addView(checkView, params);
		checkView.setImageResource(android.R.drawable.checkbox_off_background);
//		addView(checkView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		checkView.setVisibility(View.GONE);
	}

	boolean isChecked;
	private void drawCheck() {
		if (isChecked) {
			checkView.setImageResource(android.R.drawable.checkbox_on_background);
//			int color = Color.argb(0x80, 0xFF, 0, 0);
//			checkView.setImageDrawable(new ColorDrawable(color));
//			checkView.setVisibility(View.VISIBLE);
//			setBackgroundColor(Color.RED);
		} else {
//			setBackgroundColor(Color.TRANSPARENT);
//			checkView.setVisibility(View.GONE);
			checkView.setImageResource(android.R.drawable.checkbox_off_background);
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
