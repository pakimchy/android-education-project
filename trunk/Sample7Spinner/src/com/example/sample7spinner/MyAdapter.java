package com.example.sample7spinner;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	List<String> items = new ArrayList<String>();
	
	public MyAdapter() {
		
	}
	
	public void add(String item) {
		items.add(item);
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
			tv = new TextView(parent.getContext());
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(items.get(position));
		return tv;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		CheckedTextView tv;
		if (convertView == null) {
			tv = new CheckedTextView(parent.getContext());
		} else {
			tv = (CheckedTextView)convertView;
		}
		tv.setText(items.get(position));
		tv.setBackgroundColor(Color.GRAY);
		return tv;
	}

}
