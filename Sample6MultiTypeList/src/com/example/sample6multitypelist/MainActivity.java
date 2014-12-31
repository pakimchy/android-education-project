package com.example.sample6multitypelist;

import java.util.Date;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	RadioGroup group;
	EditText inputView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		group = (RadioGroup)findViewById(R.id.radioGroup1);
		inputView = (EditText)findViewById(R.id.edit_input);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		Button btn = (Button)findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = inputView.getText().toString();
				ItemData data = new ItemData();
				switch (group.getCheckedRadioButtonId()) {
				case R.id.radio_date:
					data.message = new Date().toString();
					data.type = ItemData.TYPE_DATE;
					mAdapter.add(data);
					break;
				case R.id.radio_send:
					if (keyword != null && !keyword.equals("")) {
						data.message = keyword;
						data.type = ItemData.TYPE_SEND;
						mAdapter.add(data);
						inputView.setText("");
					}
					break;
				case R.id.radio_receive:
				default:
					if (keyword != null && !keyword.equals("")) {
						data.message = keyword;
						data.type = ItemData.TYPE_RECEIVE;
						mAdapter.add(data);
						inputView.setText("");
					}
					break;
				}
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
