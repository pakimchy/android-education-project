package com.example.sample6autologin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sample6autologin.NetworkManager.OnResultListener;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class SplashActivity extends ActionBarActivity {

	Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		String name = PropertyManager.getInstnace().getUserName();
		String facebookId = PropertyManager.getInstnace().getFacebookId();
		if (!name.equals("")) {
			String password = PropertyManager.getInstnace().getPassword();
			NetworkManager.getInstance().login(name, password, new OnResultListener() {
				
				@Override
				public void onSuccess(String message) {
					Intent intent = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			});
		} else if (!facebookId.equals("")) {
			login(new Session.StatusCallback() {
				
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					if (session.isOpened()) {
						Request.newMeRequest(session, new Request.GraphUserCallback() {

							@Override
							public void onCompleted(GraphUser user,
									Response response) {
								if (user != null) {
									String userId = user.getId();
									if (PropertyManager.getInstnace().getFacebookId().equals(userId)) {
										String accessToken = Session.getActiveSession().getAccessToken();
										NetworkManager.getInstance().loginFacebook(SplashActivity.this, accessToken, new OnResultListener() {
											
											@Override
											public void onSuccess(String message) {
												if (message.equals("success")) {
													Intent intent = new Intent(SplashActivity.this, MainActivity.class);
													startActivity(intent);
													finish();
												}
											}
										});
									} else {
										moveLoginActivity();
									}
								}
							}
							
						});
					}
				}
			});
		} else {
			moveLoginActivity();
		}
	}
	
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, arg0, arg1, arg2);
		}
	}


	private void moveLoginActivity() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
	}

	private void login(Session.StatusCallback callback) {
		Session session = Session.getActiveSession();
		if (session == null) {
			session = Session.openActiveSessionFromCache(this);
		}

		if (session != null && session.isOpened()) {
			session.addCallback(callback);
			callback.call(session, null, null);
			return;
		}
		Session.openActiveSession(this, true, callback);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
