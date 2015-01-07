package com.example.exampleslidingmenu;

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

	public static final int MENU_ID_MAIN = 0;
	public static final int MENU_ID_FIRST = 1;
	public static final int MENU_ID_SECOND = 2;
	
	public interface MenuCallbacks {
		public void onMenuSelected(int menuId); 
	}
	
	ListView listView;
	ArrayAdapter<String> mAdapter;
	MenuCallbacks mCallbacks;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
		initData();
	}
	
	private void initData() {
		mAdapter.add("Main Menu");
		mAdapter.add("First Menu");
		mAdapter.add("Second Menu");
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
				if (mCallbacks != null) {
					mCallbacks.onMenuSelected(position);
				}
			}
		});
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MenuCallbacks) {
			mCallbacks = (MenuCallbacks)activity;
		} else {
			throw new ClassCastException("Activity must be implemented MenuCallbacks");
		}
	}
}
