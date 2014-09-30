package com.example.sample5customlist.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample5customlist.R;
import com.example.sample5customlist.data.ItemData;

public class ItemView extends FrameLayout {

	public interface OnLikeClickListener {
		public void onLikeClick(ItemView view, ItemData data);
	}
	
	OnLikeClickListener mListener;
	public void setOnLikeClickListener(OnLikeClickListener listener) {
		mListener = listener;
	}
	
	public ItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView descView;
	TextView likeView;
	ItemData data;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		
		iconView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		descView = (TextView)findViewById(R.id.desc);
		likeView = (TextView)findViewById(R.id.like);
		likeView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onLikeClick(ItemView.this, data);
				}
			}
		});
	}
	
	public void setData(ItemData data) {
		this.data = data;
		iconView.setImageResource(data.imageId);
		titleView.setText(data.title);
		descView.setText(data.desc);
		likeView.setText("like : " + data.like);
	}

}
