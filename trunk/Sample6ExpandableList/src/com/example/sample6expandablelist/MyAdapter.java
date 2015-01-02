package com.example.sample6expandablelist;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class MyAdapter extends BaseExpandableListAdapter {
	ArrayList<GroupItem> items = new ArrayList<GroupItem>();
	Context mContext;
	
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void put(String key, String title) {
		GroupItem group = null;
		int index = -1;
		for (int i = 0; i < items.size(); i++) {
			GroupItem item = items.get(i);
			int c = item.key.compareTo(key);
			if ( c == 0) {
				group = item;
				break;
			} else if (c > 0){
				index = i;
				break;
			}
			
		}
		if (group == null) {
			group = new GroupItem();
			group.key = key;
			group.title = key;
			if (index == -1) {
				items.add(group);
			} else {
				items.add(index, group);
			}
		}
		
		if (title != null) {
			ChildItem child = new ChildItem();
			child.title = title;
			group.children.add(child);
		}
		
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
		return (long)groupPosition << 32 | 0xFFFFFFFF;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return (long)groupPosition << 32 | childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupView view;
		if (convertView == null) {
			view = new GroupView(mContext);
		} else {
			view = (GroupView)convertView;
		}
		view.setText(items.get(groupPosition).title);
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildView view;
		if (convertView == null) {
			view = new ChildView(mContext);
		} else {
			view = (ChildView)convertView;
		}
		view.setText(items.get(groupPosition).children.get(childPosition).title);
		if (isLastChild) {
			view.setBackgroundColor(Color.RED);
		} else {
			view.setBackgroundColor(Color.WHITE);
		}
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
