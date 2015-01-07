package com.example.sample6listpopupwindow;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	ListPopupWindow listPopup;
	ArrayAdapter<String> mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.editText1);
		listPopup = new ListPopupWindow(this);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listPopup.setAdapter(mAdapter);
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String text = s.toString();
				if (text != null && !text.equals("")) {
					mAdapter.clear();
					mAdapter.add(text+"-1");
					mAdapter.add(text+"-2");
					mAdapter.add(text+"-3");
					listPopup.setAnchorView(keywordView);
					listPopup.show();
				} else {
					mAdapter.clear();
					listPopup.dismiss();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		listPopup.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = mAdapter.getItem(position);
				keywordView.setText(text);
				listPopup.dismiss();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
