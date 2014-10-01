package com.example.sample5spinnertest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<String> items = new ArrayList<String>();
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(String data) {
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
		TextView tv;
		if (convertView == null) {
			tv = new TextView(mContext);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(items.get(position));
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
		return tv;
	}

}
