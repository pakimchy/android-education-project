package com.example.samplestaggeredview;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {
	ArrayList<ImageItem> items = new ArrayList<ImageItem>();
	Context mContext;
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void addAll(ArrayList<ImageItem> items) {
		this.items.addAll(items);
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
		ItemView view;
		if (convertView == null) {
			view = new ItemView(mContext);
		} else {
			view = (ItemView)convertView;
		}
		view.setImageItem(items.get(position));
		return view;
	}

}
