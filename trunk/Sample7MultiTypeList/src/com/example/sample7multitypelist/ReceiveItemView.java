package com.example.sample7multitypelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceiveItemView extends FrameLayout {

	public ReceiveItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView messageView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.receive_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		messageView = (TextView)findViewById(R.id.text_message);
		
	}
	
	public void setData(ReceiveData data) {
		iconView.setImageResource(data.iconId);
		messageView.setText(data.message);
	}
 
}
