package com.example.sample5multitypelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SendView extends FrameLayout {

	public SendView(Context context) {
		super(context);
		init();
	}
	
	TextView textView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.send_layout, this);
		textView = (TextView)findViewById(R.id.textView1);
	}
	
	public void setText(String text) {
		textView.setText(text);
	}

}
