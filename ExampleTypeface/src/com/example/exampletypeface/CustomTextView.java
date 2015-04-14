package com.example.exampletypeface;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

	public CustomTextView(Context context) {
		super(context);
		init();
	}
	
	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setTypeface(TypefaceManager.getInstance().getTypeface(getContext(),
				TypefaceManager.FONT_NAMUM));
	}

}
