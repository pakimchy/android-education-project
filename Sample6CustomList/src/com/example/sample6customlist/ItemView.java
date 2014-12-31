package com.example.sample6customlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	ImageView iconView;
	TextView titleView;
	TextView descView;
	TextView likeView;
	ItemData mItem;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
		descView = (TextView)findViewById(R.id.text_desc);
		likeView = (TextView)findViewById(R.id.text_like);
	}
	
	public void setItemData(ItemData item) {
		mItem = item;
		iconView.setImageResource(item.iconId);
		titleView.setText(item.title);
		descView.setText(item.desc);
		likeView.setText(""+item.like);
	}

	
}
