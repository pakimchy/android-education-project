package com.example.sample6compoundwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyItemView extends FrameLayout {

	public MyItemView(Context context) {
		super(context);
		init();
	}

	public MyItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	ImageView iconView;
	TextView titleView;
	MyItem mItem;
	
	public interface OnMyIconClickListener {
		public void onMyIconClick(View v, MyItem item);
	}
	OnMyIconClickListener mListener;
	
	public void setOnMyIconClickListener(OnMyIconClickListener listener) {
		mListener = listener;
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onMyIconClick(MyItemView.this, mItem);
				}
			}
		});
		titleView = (TextView)findViewById(R.id.text_title);
	}
	
	public void setMyItem(MyItem item) {
		mItem = item;
		iconView.setImageResource(item.iconResId);
		titleView.setText(item.title);
	}
	
	public String getTitle() {
		return (mItem==null)?null:mItem.title;
	}

}
