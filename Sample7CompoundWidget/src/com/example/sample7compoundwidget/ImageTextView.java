package com.example.sample7compoundwidget;

import com.example.sample7compoundwidget.ItemView.OnImageClickListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageTextView extends LinearLayout {

	public ImageTextView(Context context) {
		super(context);
		init();
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	ImageView iconView;
	TextView titleView;
	ItemData mData;
	
	public interface OnImageClickListener {
		public void onImageClicked(ImageTextView view, ItemData data);
	}
	private OnImageClickListener mListener;	
	public void setOnImageClickListener(OnImageClickListener listener) {
		mListener = listener;
	}
	
	private void init() {
		setOrientation(LinearLayout.VERTICAL);
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onImageClicked(ImageTextView.this, mData);
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
