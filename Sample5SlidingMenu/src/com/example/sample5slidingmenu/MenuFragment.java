package com.example.sample5slidingmenu;

import java.util.ArrayList;

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

	public static final int MENU_MAIN = 0;
	public static final int MENU_ONE = 1;
	public static final int MENU_TWO = 2;
	
	public interface MenuClickListener {
		public void selectMenu(int menu);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>());
		initData();
	}
	
	private void initData() {
		mAdapter.add("Main");
		for(int i = 0; i < 2; i++) {
			mAdapter.add("Menu"+i);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_menu, container, false);
	}
	
	ListView listView;
	ArrayAdapter<String> mAdapter;
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView)view.findViewById(R.id.listView1);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Activity activity = getActivity();
				if (activity instanceof MenuClickListener) {
					((MenuClickListener)activity).selectMenu(position);
				}				
			}
		});
	}
}
