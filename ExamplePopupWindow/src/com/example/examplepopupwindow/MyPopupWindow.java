package com.example.examplepopupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MyPopupWindow extends PopupWindow {
	Context mContext;
	public MyPopupWindow(Context context) {
		super(context);
		mContext = context;
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		View view = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
		setContentView(view);
		Button btn = (Button)view.findViewById(R.id.btn_menu1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "M1 Clicked", Toast.LENGTH_SHORT).show();
				dismiss();
			}
		});
	}
}
