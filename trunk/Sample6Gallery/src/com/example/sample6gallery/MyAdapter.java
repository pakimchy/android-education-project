package com.example.sample6gallery;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {
	ArrayList<Integer> items = new ArrayList<Integer>();
	Context mContext;
	int width;
	int height;
	
	public MyAdapter(Context context) {
		mContext = context;
		width = context.getResources().getDimensionPixelSize(R.dimen.image_width);
		height = context.getResources().getDimensionPixelSize(R.dimen.image_height);
	}

	public void add(int regId) {
		items.add(regId);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return (items.size() == 0)?0:Integer.MAX_VALUE;
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
		} else {
			iv = (ImageView)convertView;
		}
		iv.setImageResource(items.get(position % items.size()));
		return iv;
	}

}
