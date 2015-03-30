package com.example.sample7customlist;

import com.example.sample7customlist.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView , descView, likeView;
	ItemData mData;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
		descView = (TextView)findViewById(R.id.text_desc);
		likeView = (TextView)findViewById(R.id.text_like);
	}
	
	public void setItemData(ItemData data) {
		mData = data;
		iconView.setImageResource(data.iconId);
		titleView.setText(data.title);
		descView.setText(data.desc);
		likeView.setText(""+data.like);
	}
}
