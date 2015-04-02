package com.example.sample7fragmentchildfragmentmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OneFragment extends Fragment {
	FragmentManager cFM;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		cFM = getChildFragmentManager();
		Button btn = (Button)view.findViewById(R.id.btn_sub_tab1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				replaceSubTab1();
			}
		});
		btn = (Button)view.findViewById(R.id.btn_sub_tab2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				replaceSubTab2();
			}
		});
		if (savedInstanceState == null) {
			replaceSubTab1();
		}
		return view;
	}
	
	private void replaceSubTab1() {
		ChildOneFragment f = new ChildOneFragment();
		FragmentTransaction ft = cFM.beginTransaction();
		ft.replace(R.id.child_container, f);
		ft.commit();
	}
	private void replaceSubTab2() {
		ChildTwoFragment f = new ChildTwoFragment();
		FragmentTransaction ft = cFM.beginTransaction();
		ft.replace(R.id.child_container, f);
		ft.commit();
	}
}
