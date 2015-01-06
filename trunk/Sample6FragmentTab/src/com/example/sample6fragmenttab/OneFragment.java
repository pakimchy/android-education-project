package com.example.sample6fragmenttab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OneFragment extends Fragment {

	FragmentTabHost tabHost;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_one, container, false);
		tabHost = (FragmentTabHost)v.findViewById(R.id.tabhost);
		tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(tabHost.newTabSpec("ct1").setIndicator("CT1"), ChildOneFragment.class, null);
		tabHost.addTab(tabHost.newTabSpec("ct2").setIndicator("CT2"), ChildTwoFragment.class, null);
		return v;
	}
}
