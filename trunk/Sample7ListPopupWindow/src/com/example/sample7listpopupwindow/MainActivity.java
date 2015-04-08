package com.example.sample7listpopupwindow;

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

	ListPopupWindow mListWindow;
	ArrayAdapter<String> mAdapter;
	EditText keywordView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        mListWindow = new ListPopupWindow(this);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mListWindow.setAdapter(mAdapter);
        mListWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = (String)mListWindow.getListView().getItemAtPosition(position);
				keywordView.setText(text);
				mListWindow.dismiss();
			}
		});
        
        keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				if (keyword.length() > 0) {
					mAdapter.clear();
					mAdapter.add(keyword + "-1");
					mAdapter.add(keyword + "-2");
					mAdapter.add(keyword + "-3");
					mListWindow.setAnchorView(keywordView);
					mListWindow.show();
				} else {
					mAdapter.clear();
					mAdapter.add("fav-1");
					mAdapter.add("fav-2");
					mAdapter.add("fav-3");
					mListWindow.setAnchorView(keywordView);
					mListWindow.show();
					
//					mListWindow.dismiss();
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
        
        
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	super.onWindowFocusChanged(hasFocus);
    	if (hasFocus) {
    		mAdapter.clear();
    		mAdapter.add("fav-1");
    		mAdapter.add("fav-2");
    		mAdapter.add("fav-3");
    		mListWindow.setAnchorView(keywordView);
    		mListWindow.show();
    	}
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
