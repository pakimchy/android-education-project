package com.example.sample5fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentThree extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.f3_layout, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button btn = (Button)view.findViewById(R.id.btn_sub1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentSubOne f = new FragmentSubOne();
				FragmentTransaction ft = getChildFragmentManager().beginTransaction();
				ft.replace(R.id.childcontainer, f);
				ft.commit();
			}
		});
		
		btn = (Button)view.findViewById(R.id.btn_sub2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentSubTwo f = new FragmentSubTwo();
				FragmentTransaction ft = getChildFragmentManager().beginTransaction();
				ft.replace(R.id.childcontainer, f);
				ft.commit();
			}
		});
		
		FragmentSubOne f = new FragmentSubOne();
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.replace(R.id.childcontainer, f);
		ft.commit();
	}
}
