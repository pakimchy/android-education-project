package com.example.sampleautocompletetextnetwork;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.sampleautocompletetextnetwork.NetworkManager.OnResultListener;

public class NaverMovieAdapter extends BaseAdapter implements Filterable {

	ArrayList<MovieItem> items = new ArrayList<MovieItem>();
	Context mContext;
	private static final int NOT_INIT = -1;
	public static final int NO_MORE = -1;
	int total = NOT_INIT;
	public NaverMovieAdapter(Context context) {
		mContext = context;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getStart() {
		if (total != NOT_INIT && total <= items.size()) {
			return NO_MORE;
		}
		return items.size() + 1;
	}
	
	public void add(MovieItem item) {
		items.add(item);
		notifyDataSetChanged();
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
	public MovieItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView v;
		if (convertView == null) {
			v = new ItemView(mContext);
		} else {
			v = (ItemView)convertView;
		}
		v.setMovieItem(items.get(position));
		return v;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new NaverMovieFilter();
		}
		return mFilter;
	}
	
	Filter mFilter;
	class NaverMovieFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint == null || constraint.length() == 0) {
				return null;
			}
			results.count = 1;
			results.values = constraint.toString();
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results == null) return;
			String keyword = (String)results.values;
			NetworkManager.getInstance().getNaverMovieData(mContext, keyword, 10, 1, new OnResultListener<NaverMovie>() {
				
				@Override
				public void onSuccess(NaverMovie result) {
					items.clear();
					items.addAll(result.item);
					notifyDataSetChanged();
				}
				
				@Override
				public void onFail(int code) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
	}

}
