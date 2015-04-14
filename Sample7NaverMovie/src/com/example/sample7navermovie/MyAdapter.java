package com.example.sample7navermovie;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	List<MovieItem> items = new ArrayList<MovieItem>();
	public MyAdapter() {
		
	}
	
	public void addAll(List<MovieItem> list) {
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
		MovieItemView view;
		if(convertView == null) {
			view = new MovieItemView(parent.getContext());
		} else {
			view = (MovieItemView)convertView;
		}
		view.setMovieItem(items.get(position));
		return view;
	}

}
