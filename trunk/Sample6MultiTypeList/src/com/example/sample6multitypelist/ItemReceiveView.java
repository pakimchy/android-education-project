package com.example.sample6multitypelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemReceiveView extends FrameLayout {

	public ItemReceiveView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView messageView;
	ItemData mItem;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.receive_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		messageView = (TextView)findViewById(R.id.text_message);
	}
	
	public void setItemData(ItemData item) {
		mItem = item;
		messageView.setText(item.message);
	}

	
}
