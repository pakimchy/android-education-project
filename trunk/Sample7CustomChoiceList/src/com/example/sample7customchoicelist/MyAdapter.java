package com.example.sample7customchoicelist;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
	public String getItem(int position) {
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
			view = new ItemView(parent.getContext());
		} else {
			view = (ItemView)convertView;
		}
		view.setText(items.get(position));
		return view;
	}

}
