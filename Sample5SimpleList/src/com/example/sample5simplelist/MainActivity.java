package com.example.sample5simplelist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	EditText keywordView;
	ArrayAdapter<String> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.editText1);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item = (String)listView.getItemAtPosition(position);
				Toast.makeText(MainActivity.this, "selected item : " + item, Toast.LENGTH_SHORT).show();
			}
		});
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = keywordView.getText().toString();
				if (text != null && !text.equals("")) {
					mAdapter.add(text);
					listView.smoothScrollToPosition(mAdapter.getCount() - 1);
				}
			}
		});
		
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		listView.setAdapter(mAdapter);
		
		initData();
	}
	
	private void initData() {
//		for (int i = 0 ; i < 10; i++) {
//			mAdapter.add("item " + i);
//		}
		String[] array = getResources().getStringArray(R.array.listitem);

		for (String str : array) {
			mAdapter.add(str);
		}
		
//		List<String> list = Arrays.asList(array);
//		
//		for (String item : list) {
//			
//		}
//		
//		String[] aa = new String[list.size()];
//		list.toArray(aa);
		
//		for (int i = 0 ; i < array.length; i++) {
//			String str = array[i];
//		}

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
