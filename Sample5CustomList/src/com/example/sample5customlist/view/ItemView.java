package com.example.sample5customlist.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample5customlist.R;
import com.example.sample5customlist.data.ItemData;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView descView;
	ItemData data;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		
		iconView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		descView = (TextView)findViewById(R.id.desc);
		
	}
	
	public void setData(ItemData data) {
		this.data = data;
		iconView.setImageResource(data.imageId);
		titleView.setText(data.title);
		descView.setText(data.desc);
	}

}
