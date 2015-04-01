package com.example.exampleaddressbook;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AddressBookAdapter extends BaseAdapter {
	
	List<Address> items = new ArrayList<Address>();

	public void addAll(List<Address> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void add(Address item) {
		this.items.add(item);
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
	public Address getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AddressItemView view;
		if (convertView == null) {
			view = new AddressItemView(parent.getContext());
		} else {
			view = (AddressItemView)convertView;
		}
		view.setAddress(items.get(position));
		return view;
	}

}
