package com.example.sample7expandablelist;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class MyAdapter extends BaseExpandableListAdapter {
	List<GroupData> groups = new ArrayList<GroupData>();
	
	public MyAdapter() {
		
	}
	
	
	public void addChild(String groupName, ChildData child) {
		GroupData group = null;
		for (GroupData g : groups) {
			if (g.name.equals(groupName)) {
				group = g;
				break;
			}
		}
		if (group == null) {
			group = new GroupData();
			group.name = groupName;
			groups.add(group);
		}
		
		if (child != null) {
			group.children.add(child);
		}
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		long id = ((long)groupPosition)<<32 | 0xFFFFFFFF;
		return id;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		long id = ((long)groupPosition)<<32 | childPosition;
		return id;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupItemView view;
		if (convertView == null || !(convertView instanceof GroupItemView)) {
			view = new GroupItemView(parent.getContext());
		} else {
			view = (GroupItemView)convertView;
		}
		view.setGroupData(groups.get(groupPosition));
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildItemView view;
		if (convertView == null || !(convertView instanceof ChildItemView)) {
			view = new ChildItemView(parent.getContext());
		} else {
			view = (ChildItemView)convertView;
		}
		if (isLastChild) {
			view.setBackgroundColor(Color.GREEN);
		} else {
			view.setBackgroundColor(Color.TRANSPARENT);
		}
		view.setChildData(groups.get(groupPosition).children.get(childPosition));
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
