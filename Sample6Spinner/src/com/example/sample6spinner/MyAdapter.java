package com.example.sample6spinner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	ArrayList<String> items = new ArrayList<String>();
	Context mContext;
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(String item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<String> items) {
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
		TextView tv;
		if (convertView == null) {
			tv = new TextView(mContext);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(items.get(position));
		tv.setBackgroundColor(Color.GREEN);
		return tv;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = new TextView(mContext);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(items.get(position));
		tv.setBackgroundColor(Color.YELLOW);
		return tv;
	}

}
