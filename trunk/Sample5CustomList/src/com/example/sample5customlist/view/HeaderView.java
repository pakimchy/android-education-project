package com.example.sample5customlist.view;

import com.example.sample5customlist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class HeaderView extends FrameLayout {

	public HeaderView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.header_layout, this);
	}

}
