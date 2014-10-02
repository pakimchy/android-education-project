package com.example.sample5fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOne extends Fragment {

	String message;

	public interface OnFragmentMessage {
		public void onFragmentMessage(String message);
	}
	
	public interface OnResultListener {
		public void onResult(String message);
	}
	
	OnResultListener mListener;
	public void setOnResultListener(OnResultListener listener) {
		mListener = listener;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		if (b != null) {
			message = b.getString("message");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f1_layout, container , false);
		return v;
	}
	TextView textView;
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		textView = (TextView)view.findViewById(R.id.textView1);
		Button btn = (Button)view.findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "message : " + message, Toast.LENGTH_SHORT).show();
				((MainActivity)getActivity()).onFragmentDataChanged("i am fragment one");
			}
		});
		
		btn = (Button)view.findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Activity activity = getActivity();
				if (activity instanceof OnFragmentMessage) {
					((OnFragmentMessage)activity).onFragmentMessage("i am fragment!!!");
				}
			}
		});
		
		btn = (Button)view.findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onResult("I am FragmentOne!!!");
				}
			}
		});
	}
}
