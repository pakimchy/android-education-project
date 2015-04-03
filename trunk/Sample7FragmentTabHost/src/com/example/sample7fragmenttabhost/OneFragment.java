package com.example.sample7fragmenttabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OneFragment extends Fragment {
	FragmentTabHost tabhost;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		tabhost = (FragmentTabHost)view.findViewById(R.id.tabhost);
		tabhost.setup(getActivity(), getChildFragmentManager(), R.id.realchildtabcontent);
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("TAB2"), TwoFragment.class, null);
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("TAB3"), ThreeFragment.class, null);
		return view;
	}
}
