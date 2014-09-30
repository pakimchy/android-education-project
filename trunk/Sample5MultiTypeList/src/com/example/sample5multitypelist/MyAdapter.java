package com.example.sample5multitypelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {
	public static final int TYPE_COUNT = 3;
	
	public static final int VIEW_TYPE_DATE = 0;
	public static final int VIEW_TYPE_RECEIVE = 1;
	public static final int VIEW_TYPE_SEND = 2;
	
	Context mContext;
	ArrayList<ItemData> items = new ArrayList<ItemData>();
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(ItemData data) {
		items.add(data);
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
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}
	
	@Override
	public int getItemViewType(int position) {
		switch(items.get(position).type) {
		case ItemData.TYPE_DATE:
			return VIEW_TYPE_DATE;
		case ItemData.TYPE_RECEIVE :
			return VIEW_TYPE_RECEIVE;
		case ItemData.TYPE_SEND :
			return VIEW_TYPE_SEND;
		}
		return VIEW_TYPE_DATE;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch(getItemViewType(position)) {
		case VIEW_TYPE_DATE :
			DateView dateView;
			if (convertView == null || !(convertView instanceof DateView)) {
				dateView = new DateView(mContext);
			} else {
				dateView = (DateView)convertView;
			}
			dateView.setText(items.get(position).text);
			return dateView;
		case VIEW_TYPE_RECEIVE :
			ReceiveView receiveView;
			if (convertView == null || !(convertView instanceof ReceiveView)) {
				receiveView = new ReceiveView(mContext);
			} else {
				receiveView = (ReceiveView)convertView;
			}
			receiveView.setText(items.get(position).text);
			return receiveView;
		case VIEW_TYPE_SEND :
			SendView sendView;
			if (convertView == null || !(convertView instanceof SendView)) {
				sendView = new SendView(mContext);
			} else {
				sendView = (SendView)convertView;
			}
			sendView.setText(items.get(position).text);
			return sendView;
		}
		return null;
	}

}
