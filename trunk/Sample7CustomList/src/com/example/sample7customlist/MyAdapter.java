package com.example.sample7customlist;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter implements ItemView.OnButtonClickListener {

	List<ItemData> items = new ArrayList<ItemData>();
	ItemView.OnButtonClickListener mListener;
	public MyAdapter(ItemView.OnButtonClickListener listener) {
		mListener = listener;
	}
	
	public void add(ItemData item) {
		items.add(item);
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
			view = new ItemView(parent.getContext());
			view.setOnButtonClickListener(this);
		} else {
			view = (ItemView)convertView;
		}		
		view.setItemData(items.get(position));		
		return view;
	}

	public interface OnAdapterListener {
		public void onAdapterAction(Adapter adapter, View view, ItemData data);
	}
	OnAdapterListener mAdapterListener;
	public void setOnAdapterListener(OnAdapterListener listener) {
		mAdapterListener = listener;
	}
	@Override
	public void onButtonClick(View view, ItemData data) {
		if (mAdapterListener != null) {
			mAdapterListener.onAdapterAction(this, view, data);
		}
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

}
