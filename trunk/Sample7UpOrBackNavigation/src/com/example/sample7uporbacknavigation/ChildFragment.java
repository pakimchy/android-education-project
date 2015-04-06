package com.example.sample7uporbacknavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ChildFragment extends Fragment {

	int mNumber;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_child, container, false);
		TextView tv = (TextView)view.findViewById(R.id.text_number);
		mNumber = getArguments().getInt("number");
		tv.setText("number : " + mNumber);
		Button btn = (Button)view.findViewById(R.id.btn_next);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ChildActivity)getActivity()).addFragment(mNumber + 1);
			}
		});
		return view;
	}
}
