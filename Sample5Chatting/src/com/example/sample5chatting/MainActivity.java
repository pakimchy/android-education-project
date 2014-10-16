package com.example.sample5chatting;

import java.util.ArrayList;

import org.jivesoftware.smack.SmackAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample5chatting.XMPPManager.OnLoginListener;
import com.example.sample5chatting.XMPPManager.OnRosterListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<UserInfo> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SmackAndroid.init(this);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<UserInfo>(this, android.R.layout.simple_list_item_1,new ArrayList<UserInfo>());
		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UserInfo info = (UserInfo)listView.getItemAtPosition(position);
				Intent i = new Intent(MainActivity.this, ChattingActivity.class);
				i.putExtra("userId", info.user.getUser());
				startActivity(i);
			}
		});
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				XMPPManager.getInstance().login("test2701", "1111", new OnLoginListener() {
					
					@Override
					public void onSuccess() {
						XMPPManager.getInstance().getRoster(new OnRosterListener() {
							
							@Override
							public void onResult(ArrayList<UserInfo> list) {
								for (UserInfo ui : list) {
									mAdapter.add(ui);
								}
							}
						});
					}
					
					@Override
					public void onFail() {
						Toast.makeText(MainActivity.this, "login fail",Toast.LENGTH_SHORT).show();
					}
				});
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
