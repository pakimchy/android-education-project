package com.example.sample6slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment {
	ListView listView;
	ArrayAdapter<String> mAdapter;
	
	public static final int MENU_INVALID = -1;
	public static final int MENU_MAIN = 0;
	public static final int MENU_ONE = 1;
	public static final int MENU_TWO = 2;
	
	public interface MenuCallback {
		public void selectMenu(int menuId);
	}
	
	MenuCallback callback;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MenuCallback) {
			callback = (MenuCallback)activity;
		} else {
			// Throws...
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_layout, container, false);
		listView = (ListView)view.findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		mAdapter.add("main");
		mAdapter.add("one");
		mAdapter.add("two");
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (callback != null) {
					int menuId = getMenuId(position);
					if (menuId != MENU_INVALID) {
						callback.selectMenu(menuId);
					}
				}
			}
		});
		return view;
	}
	
	public int getMenuId(int position) {
		switch(position) {
		case 0 :
			return MENU_MAIN;
		case 1 :
			return MENU_ONE;
		case 2 :
			return MENU_TWO;
		}
		return MENU_INVALID;
	}
}
