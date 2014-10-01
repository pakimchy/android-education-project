package com.example.sample5expandablelist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter {

	Context mContext;
	ArrayList<GroupItemData> items = new ArrayList<GroupItemData>();
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void add(String groupKey, ChildItemData data) {
		GroupItemData group = null;
		for (GroupItemData item : items) {
			if (item.groupTitle.equals(groupKey)) {
				group = item;
				break;
			}
		}
		if (group == null) {
			group = new GroupItemData();
			group.groupTitle = groupKey;
			items.add(group);
		}
		
		group.children.add(data);
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
		return items.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return items.get(groupPosition).children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return items.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return items.get(groupPosition).children.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return (long)groupPosition << 32;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return ((long)groupPosition) << 32 | (long)childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = (TextView)LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
			tv.setPadding(50, 0, 0, 0);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(items.get(groupPosition).groupTitle);
		return tv;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = (TextView)LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
		} else {
			tv = (TextView)convertView;
		}
		tv.setText(items.get(groupPosition).children.get(childPosition).title);
		return tv;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
