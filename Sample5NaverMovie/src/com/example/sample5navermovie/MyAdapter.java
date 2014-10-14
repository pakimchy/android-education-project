package com.example.sample5navermovie;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<MovieItem> items  = new ArrayList<MovieItem>();
	int totalCount;
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void clearAll() {
		items.clear();
		notifyDataSetChanged();
	}
	public void addAll(ArrayList<MovieItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void setTotal(int total) {
		this.totalCount = total;
	}
	
	public int getTotal() {
		return totalCount;
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
		
		view.setMovieItem(items.get(position));
		
		return view;
	}
	
}
