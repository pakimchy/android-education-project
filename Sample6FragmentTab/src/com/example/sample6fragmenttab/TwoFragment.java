package com.example.sample6fragmenttab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TwoFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	ActionBar actionBar;
	public ActionBar getActionBar() {
		if (actionBar == null) {
			actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
		}
		return actionBar;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActionBar().setTitle("Fragment2");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("Fragment 2");
		return tv;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.f2_menu, menu);
	}
	
}
