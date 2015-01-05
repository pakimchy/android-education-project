package com.example.sample6fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TabOneFragment extends Fragment {

	EditText editView;

	String message;
	
	public interface MessageReceiver {
		public void setMessage(String message);
	}
	
	
	public interface OnMessageListener {
		public void onReceiveMessage(String message);
	}
	OnMessageListener mListener;
	public void setOnMessageListener(OnMessageListener listener) {
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
	
	public void setMessage(String message) {
		if (messageView != null) {
			messageView.setText(message);
		}
	}
	
	TextView messageView;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab1, container, false);
		editView = (EditText)view.findViewById(R.id.edit_input);
		// ...
		messageView = (TextView)view.findViewById(R.id.text_message);
		messageView.setText(message);
		
		Button btn = (Button)view.findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Fragment Button Click", Toast.LENGTH_SHORT).show();
				Activity activity = getActivity();
				if (activity instanceof MessageReceiver) {
					((MessageReceiver)getActivity()).setMessage(editView.getText().toString());
				}
				if (mListener != null) {
					mListener.onReceiveMessage(editView.getText().toString());
				}
			}
		});
		return view;
	}
	
}
