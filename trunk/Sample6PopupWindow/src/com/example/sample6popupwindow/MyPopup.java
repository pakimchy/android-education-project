package com.example.sample6popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class MyPopup extends PopupWindow {

	public interface OnPopupListener {
		public void onButtonClick(int index);
	}
	OnPopupListener mListener;
	
	public void setOnPopupListener(OnPopupListener listener) {
		mListener = listener;
	}
	
	public MyPopup(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		Button btn = (Button)view.findViewById(R.id.btn_one);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onButtonClick(0);
				}
				dismiss();
			}
		});
	}
}
