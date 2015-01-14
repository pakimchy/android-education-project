package com.example.sample6navermovie;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MovieAdapter extends BaseAdapter {
	ArrayList<MovieItem> items = new ArrayList<MovieItem>();
	Context mContext;
	public MovieAdapter(Context context) {
		mContext = context;
	}
	
	public void addAll(ArrayList<MovieItem> items) {
		this.items.addAll(items);
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
		MovieItemView view;
		if (convertView == null) {
			view = new MovieItemView(mContext);
		} else {
			view = (MovieItemView)convertView;
		}
		view.setMovieItem(items.get(position));
		return view;
	}

}
