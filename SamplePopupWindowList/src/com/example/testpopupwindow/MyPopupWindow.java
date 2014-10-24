package com.example.testpopupwindow;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MyPopupWindow extends PopupWindow {

	DropDownListView listView;
	ArrayAdapter<String> mAdapter;
	Context mContext;
	public MyPopupWindow(Context context) {
		super(context);
		mContext = context;
		listView = new DropDownListView(context);
		setContentView(listView);
		setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, new ArrayList<String>());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = (String)listView.getItemAtPosition(position);
				Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
			}
		});
		
		mAdapter.add("item1");
		mAdapter.add("item2");
	}
}
