package com.example.testtabpagertest;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class MainFragment extends Fragment {
	TabHost tabHost;
	ViewPager pager;
	TabsAdapter mAdapter;
	Bundle savedState;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		tabHost = (TabHost)view.findViewById(R.id.childtabhost);
		pager = (ViewPager)view.findViewById(R.id.pager);
		tabHost.setup();
		mAdapter = new TabsAdapter(getActivity(), getFragmentManager(), tabHost, pager);
		mAdapter.addTab(tabHost.newTabSpec("CT1").setIndicator("CT1"), ChildOneFragment.class, null);
		mAdapter.addTab(tabHost.newTabSpec("CT2").setIndicator("CT2"), ChildTwoFragment.class, null);			
		return view;
	}
	
	Handler mHandler = new Handler(Looper.getMainLooper());
	
	@Override
	public void onDestroyView() {
		super.onDestroy();
	}
}
