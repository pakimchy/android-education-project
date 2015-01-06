package com.example.exampleactionbartab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountFragment extends Fragment {

	public static final String EXTRA_COUNT = "count";
	int mCount;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		if (b != null) {
			mCount = b.getInt(EXTRA_COUNT);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		TextView tv = (TextView)inflater.inflate(android.R.layout.simple_list_item_1, container, false);
		tv.setText("count : " + mCount);
		return tv;
	}
}
