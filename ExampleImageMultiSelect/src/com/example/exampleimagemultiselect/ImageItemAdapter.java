package com.example.exampleimagemultiselect;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ImageItemAdapter extends BaseAdapter {

	List<ImageItem> items = new ArrayList<ImageItem>();
	public void add(ImageItem item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	public void clear() {
		items.clear();
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
		ListItemView view;
		if (convertView == null) {
			view = new ListItemView(parent.getContext());
		} else {
			view = (ListItemView)convertView;
		}
		view.setImageItem(items.get(position));
		return view;
	}

}
