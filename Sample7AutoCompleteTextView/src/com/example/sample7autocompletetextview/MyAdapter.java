package com.example.sample7autocompletetextview;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements Filterable {

	List<String> mOriginal = new ArrayList<String>();
	List<String> mObject;
	public MyAdapter() {
	}
	
	public void add(String text) {
		mOriginal.add(text);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (mObject == null) {
			return mOriginal.size();
		} else {
			return mObject.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (mObject == null) {
			return mOriginal.get(position);
		} else {
			return mObject.get(position);
		}
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
		tv.setText((String)getItem(position));
		return tv;
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new MyFilter();
		}
		return filter;
	}
	
	MyFilter filter;

	class MyFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint == null || constraint.toString().equals("")) {
				List<String> result = new ArrayList<String>(mOriginal);
				results.values = result;
				results.count = result.size();
			} else {
				String keyword = constraint.toString().toLowerCase();
				List<String> result = new ArrayList<String>();
				for (String s : mOriginal) {
					if (s.toLowerCase().contains(keyword)) {
						result.add(s);
					}
				}
				results.values = result;
				results.count = result.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results != null && results.count > 0) {
				mObject = (List<String>)results.values;
				notifyDataSetChanged();
			} else {
				mObject = (List<String>)results.values;
				notifyDataSetInvalidated();
			}
			
		}
		
	}
}
