package com.example.examplexmpp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examplexmpp.XMPPManager.OnActionListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<User> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				User user = (User)listView.getItemAtPosition(position);
				Intent i = new Intent(MainActivity.this, ChattingActivity.class);
				i.putExtra(ChattingActivity.EXTRA_USER_ID, user.entry.getUser());
				startActivity(i);
			}
		});
	}
	
	boolean bFirst = true;
	
	@Override
	protected void onResume() {
		super.onResume();
		String userid = PropertyManager.getInstance().getUserId();
		if (userid.equals("")) {
			if (bFirst) {
				startActivity(new Intent(this, SettingActivity.class));
				bFirst = false;
			} else {
				Toast.makeText(this, "Set your id and password", Toast.LENGTH_SHORT).show();
			}
			return;
		}
		String password = PropertyManager.getInstance().getPassword();
		if (!XMPPManager.getInstance().isLogin()) {
			XMPPManager.getInstance().login(userid, password, new OnActionListener<Void>() {

				@Override
				public void onSuccess(Void... data) {
					getRoster();
				}

				@Override
				public void onFail(int code) {
					Toast.makeText(MainActivity.this, "login fail", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			getRoster();
		}
	}
	
	private void getRoster() {
		XMPPManager.getInstance().getRoster(new OnActionListener<User>() {
			
			@Override
			public void onSuccess(User... data) {
				mAdapter.clear();
				for (User user : data) {
					mAdapter.add(user);
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(MainActivity.this, "fail roster", Toast.LENGTH_SHORT).show();
			}
		});
		
		startService(new Intent(this, ChattingService.class));
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
			startActivity(new Intent(this, SettingActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
