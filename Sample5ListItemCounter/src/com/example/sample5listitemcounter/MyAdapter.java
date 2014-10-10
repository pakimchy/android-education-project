package com.example.sample5listitemcounter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	ArrayList<ItemData> items = new ArrayList<ItemData>();
	Context mContext;
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(ItemData data) {
		items.add(data);
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
		view.setData(items.get(position));
		return view;
	}

}
