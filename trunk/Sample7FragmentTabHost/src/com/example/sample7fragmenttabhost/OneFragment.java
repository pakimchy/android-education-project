package com.example.sample7fragmenttabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class OneFragment extends Fragment {
	FragmentTabHost tabhost;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		tabhost = (FragmentTabHost)view.findViewById(R.id.tabhost);
		tabhost.setup(getActivity(), getChildFragmentManager(), R.id.realchildtabcontent);
		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("TAB1"), OneChildOneFragment.class, null);
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("TAB2"), TwoChildOneFragment.class, null);
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_one, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_f1_m1 :
			Toast.makeText(getActivity(), "F1M1 clicked", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onResume() {
		super.onResume();
		getActivity().setTitle("OneFragment");
	}
	
}
