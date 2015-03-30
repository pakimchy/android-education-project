package com.example.sample7compoundwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
	ItemData mData;
	
	public interface OnImageClickListener {
		public void onImageClicked(ItemView view, ItemData data);
	}
	private OnImageClickListener mListener;	
	public void setOnImageClickListener(OnImageClickListener listener) {
		mListener = listener;
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onImageClicked(ItemView.this, mData);
				}
			}
		});
		titleView = (TextView)findViewById(R.id.text_title);
	}
	
	public void setItemData(ItemData data) {
		mData = data;
		iconView.setImageResource(data.iconId);
		titleView.setText(data.title);
	}
	
	public String getTitle() {
		return mData.title;
	}

}
