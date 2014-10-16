package com.example.sample5chatting;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sample5chatting.XMPPManager.OnMessagReceiveListener;
import com.example.sample5chatting.XMPPManager.OnMessageSendListener;

public class ChattingActivity extends Activity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	EditText editView;
	Chat chat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.chatting_layout);
	    listView = (ListView)findViewById(R.id.listView1);
	    editView = (EditText)findViewById(R.id.editText1);
	    mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
	    listView.setAdapter(mAdapter);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String message = editView.getText().toString();
				if (message != null && !message.equals("")) {
					XMPPManager.getInstance().sendMessage(chat, message, new OnMessageSendListener() {

						@Override
						public void onMessageSent() {
							mAdapter.add("me : " + message);
							editView.setText("");
						}
						
					});
					
				}
			}
		});
	    
	    Intent intent = getIntent();
	    String userId = intent.getStringExtra("userId");
	    chat = XMPPManager.getInstance().createChat(userId, new OnMessagReceiveListener() {
			
			@Override
			public void onReceivedMessage(String from, String message) {
				mAdapter.add(from + " : " + message);
			}
		});
	}

}
