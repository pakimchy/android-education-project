package com.example.sample7menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MySearchView extends FrameLayout {

	public MySearchView(Context context) {
		super(context);
		init();
	}

	public MySearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public interface OnSearchResultListener {
		public void onSearchResult(String keyword);
	}
	OnSearchResultListener mListener;
	public void setOnSearchResultListener(OnSearchResultListener listener) {
		mListener = listener;
	}
	
	EditText keywordView;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.menu_layout, this);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		Button btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onSearchResult(keywordView.getText().toString());
				}
			}
		});
	}

	
}
