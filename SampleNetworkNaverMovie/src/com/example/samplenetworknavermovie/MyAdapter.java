package com.example.samplenetworknavermovie;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	Context mContext;
	List<MovieItem> items =new ArrayList<MovieItem>();
	
	public MyAdapter(Context context) {
		mContext =context;
	}
	
	public void addAll(List<MovieItem> items) {
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
		view.setMovieItem(items.get(position));
		return view;
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void add(MovieItem item) {
		items.add(item);
		notifyDataSetChanged();
	}

}
