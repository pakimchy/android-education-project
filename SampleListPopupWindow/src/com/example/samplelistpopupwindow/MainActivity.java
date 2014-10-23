package com.example.samplelistpopupwindow;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.ListPopupWindow;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	EditText editText;
	ListPopupWindow popupWindow;
	ArrayAdapter<MovieItem> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText)findViewById(R.id.editText1);
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().equals("")) {
					popupWindow.dismiss();
				} else {
					NetworkManager.getInstance().getNaverMovie(MainActivity.this, s.toString(), 1, 10, new NetworkManager.OnResultListener<NaverMovies>(){
						@Override
						public void onSuccess(NaverMovies result) {
							if (result.items.size() > 0) {
								mAdapter.clear();
								for (MovieItem item : result.items) {
									mAdapter.add(item);
								}
								popupWindow.show();
							} else {
								popupWindow.dismiss();
							}
						}
						
						@Override
						public void onFail(int code) {
							
						}
					});
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
		
		popupWindow = new ListPopupWindow(this);
		mAdapter = new ArrayAdapter<MovieItem>(this, android.R.layout.simple_list_item_1, new ArrayList<MovieItem>());
		popupWindow.setAdapter(mAdapter);
		popupWindow.setAnchorView(editText);
		popupWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MovieItem text = mAdapter.getItem(position);
				Toast.makeText(MainActivity.this, "item : " + text, Toast.LENGTH_SHORT).show();
				editText.setText(text.title);
				popupWindow.dismiss();
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
