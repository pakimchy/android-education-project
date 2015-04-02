package com.example.sample7fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OneFragment extends Fragment {
	TextView messageView;
	EditText inputView;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		messageView = (TextView)view.findViewById(R.id.text_message);
		inputView = (EditText)view.findViewById(R.id.edit_input);
		Button btn = (Button)view.findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				messageView.setText(inputView.getText().toString());
			}
		});
		return view;
	}
}
