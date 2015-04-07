package com.example.sample7slidingmenu;

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
	ArrayAdapter<MenuItem> mAdapter;
	
	public static final int MENU_ID_MAIN = 0;
	public static final int MENU_ID_ONE = 1;
	public static final int MENU_ID_TWO = 2;
	public static final int MENU_ID_THREE = 3;
	public interface MenuCallback {
		public void selectMenu(int menuId);
	}
	
	MenuCallback callback;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ArrayAdapter<MenuItem>(getActivity(), android.R.layout.simple_list_item_1);
		mAdapter.add(new MenuItem("Main", MENU_ID_MAIN));
		mAdapter.add(new MenuItem("Menu One", MENU_ID_ONE));
		mAdapter.add(new MenuItem("Menu Two", MENU_ID_TWO));
		mAdapter.add(new MenuItem("Menu Three", MENU_ID_THREE));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, container, false);
		listView = (ListView)view.findViewById(R.id.listView1);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MenuItem item = (MenuItem)listView.getItemAtPosition(position);
				if (callback != null) {
					callback.selectMenu(item.menuId);
				}
			}
		});
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MenuCallback) {
			callback = (MenuCallback)activity;
		}
	}
}
