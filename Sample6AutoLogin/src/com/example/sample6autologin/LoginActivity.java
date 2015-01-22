package com.example.sample6autologin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sample6autologin.NetworkManager.OnResultListener;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class LoginActivity extends ActionBarActivity {

	EditText idView;
	EditText passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		idView = (EditText)findViewById(R.id.editText1);
		passwordView = (EditText)findViewById(R.id.editText2);
		Button btn = (Button)findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String id = idView.getText().toString();
				final String password = passwordView.getText().toString();
				NetworkManager.getInstance().login(id, password, new OnResultListener() {
					
					@Override
					public void onSuccess(String message) {
						PropertyManager.getInstnace().setUserName(id);
						PropertyManager.getInstnace().setPassword(password);
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				});
			}
		});
		
		btn = (Button)findViewById(R.id.btn_signup);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_facebook);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(LoginActivity.this, true, new StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							Request.newMeRequest(session, new GraphUserCallback() {
								
								@Override
								public void onCompleted(GraphUser user, Response response) {
									if (user.getId() != null) {
										final String userId = user.getId();
										String accessToken = Session.getActiveSession().getAccessToken();
										NetworkManager.getInstance().loginFacebook(LoginActivity.this, accessToken, new NetworkManager.OnResultListener() {
											
											@Override
											public void onSuccess(String message) {
												if (message.equals("success")) {
													PropertyManager.getInstnace().setFacebookId(userId);
													Intent intent = new Intent(LoginActivity.this, MainActivity.class);
													startActivity(intent);
													finish();
												} else if (message.equals("notregistered")) {
													Intent intent = new Intent(LoginActivity.this, FacebookSignUpActivity.class);
													intent.putExtra("userId", userId);
													startActivity(intent);
												}
												
											}
											
											
										});
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
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, arg0, arg1, arg2);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
