package com.example.sample7dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class NoTitleDialogFragment extends DialogFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_LEFT_ICON);
		View view = inflater.inflate(R.layout.dialog_layout, container, false);
		Button btn = (Button)view.findViewById(R.id.btn_ok);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Ok Clicked", Toast.LENGTH_SHORT).show();
				dismiss();
			}
		});
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = 100;
		params.y = 100;
		getDialog().getWindow().setAttributes(params);
		getDialog().getWindow().setLayout(400, 400);
	}
}
