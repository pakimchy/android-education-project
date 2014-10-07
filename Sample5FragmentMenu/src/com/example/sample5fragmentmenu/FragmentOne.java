package com.example.sample5fragmentmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOne extends PagerFragment {
	ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {		
		return inflater.inflate(R.layout.f_layout, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		TextView tv = (TextView)view.findViewById(R.id.message);
		tv.setText("FragmentOne");
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.f1_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.f1_menu) {
			Toast.makeText(getActivity(), "f1 menu", Toast.LENGTH_SHORT).show();
			return true;
		}
 		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onPageCurrent() {
		super.onPageCurrent();
		actionBar.setTitle("F1 Fragment");
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setHomeButtonEnabled(false);
	}
}
