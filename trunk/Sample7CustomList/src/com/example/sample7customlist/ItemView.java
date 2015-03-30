package com.example.sample7customlist;

import com.example.sample7customlist.R.id;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
	
	public interface OnButtonClickListener {
		public void onButtonClick(View view, ItemData data);
	}
	OnButtonClickListener mListener;
	public void setOnButtonClickListener(OnButtonClickListener listener) {
		mListener = listener;
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setBackgroundColor(Color.RED);
			}
		});
		titleView = (TextView)findViewById(R.id.text_title);
		descView = (TextView)findViewById(R.id.text_desc);
		likeView = (TextView)findViewById(R.id.text_like);
		findViewById(R.id.ratingBar1).setFocusable(false);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onButtonClick(ItemView.this, mData);
				}
			}
		});
	}
	
	public void setItemData(ItemData data) {
		mData = data;
		iconView.setImageResource(data.iconId);
		titleView.setText(data.title);
		descView.setText(data.desc);
		likeView.setText(""+data.like);
		initialize();
	}
	
	public void initialize() {
		setBackgroundColor(Color.TRANSPARENT);
	}
}
