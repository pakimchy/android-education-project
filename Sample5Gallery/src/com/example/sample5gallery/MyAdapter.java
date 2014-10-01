package com.example.sample5gallery;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<Integer> items = new ArrayList<Integer>();
	int width, height;
	
	public MyAdapter(Context context) {
		mContext = context;
		width = context.getResources().getDimensionPixelSize(R.dimen.image_width);
		height = context.getResources().getDimensionPixelSize(R.dimen.image_height);
	}
	
	public void add(int resId) {
		items.add((Integer)resId);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
//		return items.size();
		if (items.size() > 0) {
			return Integer.MAX_VALUE;
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return items.get(position % items.size());
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv;
		if (convertView == null) {
			iv = new ImageView(mContext);
			Gallery.LayoutParams lp = new Gallery.LayoutParams(width, height);
			iv.setLayoutParams(lp);
			iv.setScaleType(ScaleType.FIT_XY);
		} else {
			iv = (ImageView)convertView;
		}
		iv.setImageResource(items.get(position % items.size()));
		return iv;
	}

}
