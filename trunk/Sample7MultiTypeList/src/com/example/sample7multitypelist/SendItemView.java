package com.example.sample7multitypelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SendItemView extends FrameLayout {

	public SendItemView(Context context) {
		super(context);
		init();
	}

	TextView messageView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.send_layout, this);
		messageView = (TextView)findViewById(R.id.text_message);
	}
	
	public void setData(SendData data) {
		messageView.setText(data.message);
	}

}
