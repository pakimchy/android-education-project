package com.example.sample7staggeredgrid;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	List<ImageItem> items = new ArrayList<ImageItem>();
	public MyAdapter() {
		
	}
	
	
	public void addAll(List<ImageItem> list) {
		items.addAll(list);
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
		ItemImageView view;
		if (convertView == null) {
			view = new ItemImageView(parent.getContext());
		} else {
			view = (ItemImageView)convertView;
		}
		view.setImageItem(items.get(position));
		return view;
	}

}
