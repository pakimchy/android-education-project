package com.example.sample6multitypelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter {

	ArrayList<ItemData> items = new ArrayList<ItemData>();
	Context mContext;

	public static final int VIEW_TYPE_COUNT = 3;

	public static final int VIEW_INDEX_DATE = 0;
	public static final int VIEW_INDEX_SEND = 1;
	public static final int VIEW_INDEX_RECEIVE = 2;

	public MyAdapter(Context context) {
		mContext = context;
	}

	public void add(ItemData item) {
		items.add(item);
		notifyDataSetChanged();
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		switch (items.get(position).type) {
		case ItemData.TYPE_DATE:
			return VIEW_INDEX_DATE;
		case ItemData.TYPE_SEND:
			return VIEW_INDEX_SEND;
		case ItemData.TYPE_RECEIVE:
		default:
			return VIEW_INDEX_RECEIVE;
		}
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
		case VIEW_INDEX_DATE: {
			ItemDateView view;
			if (convertView != null && convertView instanceof ItemDateView) {
				view = (ItemDateView) convertView;
			} else {
				view = new ItemDateView(mContext);
			}
			view.setItemData(items.get(position));
			return view;
		}
		case VIEW_INDEX_SEND: {
			ItemSendView view;
			if (convertView != null && convertView instanceof ItemSendView) {
				view = (ItemSendView) convertView;
			} else {
				view = new ItemSendView(mContext);
			}
			view.setItemData(items.get(position));
			return view;
		}
		case VIEW_INDEX_RECEIVE:
		default: {
			ItemReceiveView view;
			if (convertView != null && convertView instanceof ItemReceiveView) {
				view = (ItemReceiveView) convertView;
			} else {
				view = new ItemReceiveView(mContext);
			}
			view.setItemData(items.get(position));
			return view;
		}

		}
	}

}
