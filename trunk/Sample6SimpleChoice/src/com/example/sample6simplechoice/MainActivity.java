package com.example.sample6simplechoice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
//		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice);
		listView.setAdapter(mAdapter);
//		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
					int position = listView.getCheckedItemPosition();
					String text = (String)listView.getItemAtPosition(position);
					Toast.makeText(MainActivity.this, "choice : " + text, Toast.LENGTH_SHORT).show();
				} else if (listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray array = listView.getCheckedItemPositions();
					StringBuilder sb = new StringBuilder();
					boolean isFirst = true;
					for (int i = 0 ; i < array.size(); i++) {
						int position = array.keyAt(i);
						if (array.get(position)) {
							if (!isFirst) {
								sb.append(",");
							}
							sb.append((String)listView.getItemAtPosition(position));
							isFirst = false;
						}
					}
					
					Toast.makeText(MainActivity.this, "choice : " + sb.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		initData();
	}
	
	private void initData() {
		for (int i = 0 ; i < 20; i++) {
			mAdapter.add("item " + i);
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
