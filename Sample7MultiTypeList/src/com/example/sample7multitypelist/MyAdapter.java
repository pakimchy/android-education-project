package com.example.sample7multitypelist;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	List<ChatMessage> items = new ArrayList<ChatMessage>();
	private static final int VIEW_TYPE_COUNT = 3;
	private static final int VIEW_TYPE_SEND = 0;
	private static final int VIEW_TYPE_RECEIVE = 1;
	private static final int VIEW_TYPE_DATE = 2;

	public MyAdapter() {

	}

	public void add(ChatMessage item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMessage data = items.get(position);
		if (data instanceof SendData) {
			return VIEW_TYPE_SEND;
		} else if (data instanceof ReceiveData) {
			return VIEW_TYPE_RECEIVE;
		} else if (data instanceof DateData) {
			return VIEW_TYPE_DATE;
		}
		return VIEW_TYPE_SEND;
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
		switch (getItemViewType(position)) {
		case VIEW_TYPE_RECEIVE: {
			ReceiveItemView view;
			if (convertView != null && (convertView instanceof ReceiveItemView)) {
				view = (ReceiveItemView) convertView;
			} else {
				view = new ReceiveItemView(parent.getContext());
			}
			view.setData((ReceiveData) items.get(position));
			return view;
		}
		case VIEW_TYPE_DATE: {
			DateItemView view;
			if (convertView != null && (convertView instanceof DateItemView)) {
				view = (DateItemView) convertView;
			} else {
				view = new DateItemView(parent.getContext());
			}
			view.setData((DateData) items.get(position));
			return view;
		}
		case VIEW_TYPE_SEND:
		default: {
			SendItemView view;
			if (convertView != null && (convertView instanceof SendItemView)) {
				view = (SendItemView) convertView;
			} else {
				view = new SendItemView(parent.getContext());
			}
			view.setData((SendData) items.get(position));
			return view;
		}
		}
	}

}
