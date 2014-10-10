package com.example.sample5listitemcounter;

import android.content.Context;
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

	ImageView iconView;
	TextView countView;
	ItemData mData;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.icon_view);
		countView = (TextView)findViewById(R.id.textView1);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mData.startTime == -1L) {
					post(countRunnable);
				}
			}
		});
	}

	public void setData(ItemData data) {
		mData = data;
		iconView.setImageResource(data.imageId);
		removeCallbacks(countRunnable);
		if (data.startTime == -1L) {
			countView.setText("not start");
		} else {
			post(countRunnable);
		}
	}
	
	Runnable countRunnable = new Runnable() {
		
		@Override
		public void run() {
			long currentTime = System.currentTimeMillis();
			if (mData.startTime == -1L) {
				mData.startTime = currentTime;
				countView.setText("count : 10");
				postDelayed(this, 1000);
			} else {
				int gap = (int) (currentTime - mData.startTime);
				int count = 10 - (gap / 1000);
				if (count > 0) {
					countView.setText("count : " + count);
					int interval = 1000 - (gap % 1000);
					postDelayed(this, interval);
				} else {
					countView.setText("count done");
				}
			}
		}
	};
}
