package com.example.sample5gridview;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<Integer> items = new ArrayList<Integer>();
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(int resId) {
		items.add((Integer)resId);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
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
		} else {
			iv = (ImageView)convertView;
		}
		iv.setImageResource(items.get(position));
		return iv;
	}

}
