package com.example.testviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChildFragment extends Fragment {
	ViewPager pager;
	MyFragmentPagerAdapter mAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout, container, false);
		pager = (ViewPager)v.findViewById(R.id.pager);
		mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
		pager.setAdapter(mAdapter);
		return v;
	}
}
