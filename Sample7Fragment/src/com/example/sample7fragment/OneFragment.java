package com.example.sample7fragment;

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

public class OneFragment extends Fragment {
	TextView messageView;
	EditText inputView;
	String message = "";

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
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		messageView = (TextView) view.findViewById(R.id.text_message);
		messageView.setText(message);
		inputView = (EditText) view.findViewById(R.id.edit_input);
		Button btn = (Button) view.findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				messageView.setText(inputView.getText().toString());
			}
		});
		return view;
	}
	
	public String getEditValue() {
		if (inputView != null) {
			return inputView.getText().toString();
		}
		return null;
	}
	
	Activity mActivity;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
	
	public void setText(String text) {
		if (getActivity() != null) {
			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mActivity, "fragment not insert", Toast.LENGTH_SHORT).show();
		}
	}
}
