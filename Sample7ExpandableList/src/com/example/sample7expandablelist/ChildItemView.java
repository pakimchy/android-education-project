package com.example.sample7expandablelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ChildItemView extends FrameLayout {

	public ChildItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView nameView, messageView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.child_item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		nameView = (TextView)findViewById(R.id.text_name);
		messageView = (TextView)findViewById(R.id.text_message);
	}
	
	public void setChildData(ChildData data) {
		iconView.setImageResource(data.iconId);
		nameView.setText(data.name);
		messageView.setText(data.message);
	}

}
