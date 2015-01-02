package com.example.sample6autocompletetextview;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class MyAdapter extends BaseAdapter implements Filterable {

	ArrayList<Person> items = new ArrayList<Person>();
	Context mContext;
	
	ArrayList<Person> results = new ArrayList<Person>();
	
	
	public MyAdapter(Context context) {
		mContext = context;
	}

	public void add(Person p) {
		items.add(p);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return results.size();
	}

	@Override
	public Object getItem(int position) {
		return results.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PersonView view;
		if (convertView == null) {
			view = new PersonView(mContext);
		} else {
			view = (PersonView)convertView;
		}
		view.setPerson(results.get(position));
		return view;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new MyFilter();
		}
		return mFilter;
	}
	
	Filter mFilter;

	class MyFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			String s = constraint.toString();
			FilterResults result = new FilterResults();
			ArrayList<Person> list = new ArrayList<Person>();
			for (Person p : items) {
				if (p.name.contains(s)) {
					list.add(p);
				}
			}
			result.count = list.size();
			result.values = list;
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults fr) {
			results.clear();
			if (fr == null || fr.count == 0 || fr.values == null) return;
			results.addAll((ArrayList<Person>)fr.values);
			notifyDataSetChanged();
		}
		
	}
}
