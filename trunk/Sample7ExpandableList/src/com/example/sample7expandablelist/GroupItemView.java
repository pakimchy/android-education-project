package com.example.sample7expandablelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GroupItemView extends FrameLayout {

	public GroupItemView(Context context) {
		super(context);
		init();
	}
	
	TextView nameView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.group_item_layout, this);
		nameView = (TextView)findViewById(R.id.text_name);
	}
	
	public void setGroupData(GroupData data) {
		nameView.setText(data.name);
	}

}
