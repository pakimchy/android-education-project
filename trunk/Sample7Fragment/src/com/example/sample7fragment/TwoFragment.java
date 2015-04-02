package com.example.sample7fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class TwoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_two, container, false);
		Button btn = (Button)view.findViewById(R.id.btn_go_activity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), OtherActivity.class);
				intent.putExtra("message", "test");
				startActivityForResult(intent, 0);
			}
		});
		
		btn = (Button)view.findViewById(R.id.btn_finish);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				getActivity().finish();
				((MainActivity)getActivity()).calledFragment("i am twofragment");
			}
		});
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			String result = data.getStringExtra("result");
			Toast.makeText(getActivity(), "result : " + result, Toast.LENGTH_SHORT).show();
		}
	}
}
