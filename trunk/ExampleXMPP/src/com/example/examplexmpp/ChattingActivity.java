package com.example.examplexmpp;

import java.util.List;

import org.jivesoftware.smack.Chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.examplexmpp.XMPPManager.OnActionListener;

public class ChattingActivity extends ActionBarActivity {

	public static final String EXTRA_USER_ID = "userid";
	ListView listView;
	ArrayAdapter<ChatMessage> mAdapter;
	EditText messageView;
	Chat mChat;
	String mUserId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chatting);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<ChatMessage>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		messageView = (EditText)findViewById(R.id.edit_message);
		Button btn = (Button)findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String message = messageView.getText().toString();
				XMPPManager.getInstance().sendChatMessage(mChat, message, new OnActionListener<Void>() {
					
					@Override
					public void onSuccess(Void... data) {
						ChatMessage cm = new ChatMessage();
						cm.mateId = mUserId;
						cm.message = message;
						cm.bReceived = false;
						cm.created = System.currentTimeMillis();
						DBManager.getInstance().addChatMessage(cm);
						mAdapter.add(cm);
					}
					
					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		mUserId = getIntent().getStringExtra(EXTRA_USER_ID);
		mChat = XMPPManager.getInstance().getChat(mUserId);
		
	}
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String mateid = intent.getStringExtra(ChattingService.EXTRA_MATE_ID);
			if (mUserId.equals(mateid)) {
				refreshData();
				setResultCode(Activity.RESULT_OK);
			}
		}
	};

	private void refreshData() {
		mAdapter.clear();
		List<ChatMessage> list = DBManager.getInstance().getChatMessageList(mUserId);
		for (ChatMessage cm : list) {
			mAdapter.add(cm);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(ChattingService.ACTION_CHATTING_MESSAGE);
		registerReceiver(mReceiver, filter);
		refreshData();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mReceiver);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chatting, menu);
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
