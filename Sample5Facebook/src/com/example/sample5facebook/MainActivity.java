package com.example.sample5facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true, new StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							String accessToken = session.getAccessToken();
							Request.newMeRequest(session, new GraphUserCallback() {
								
								@Override
								public void onCompleted(GraphUser user, Response response) {
									if (user != null) {
										Toast.makeText(MainActivity.this, "my id : " + user.getId(), Toast.LENGTH_SHORT).show();
									}
								}
							}).executeAsync();
						}
					}
				});
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
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
