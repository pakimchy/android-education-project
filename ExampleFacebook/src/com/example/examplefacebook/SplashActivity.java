package com.example.examplefacebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.examplefacebook.NetworkManager.OnResultListener;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class SplashActivity extends ActionBarActivity {

	AccessTokenTracker accessTokenTracker;
	CallbackManager callbackManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		callbackManager = CallbackManager.Factory.create();
		accessTokenTracker = new AccessTokenTracker() {
			
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
					AccessToken currentAccessToken) {
				String userid = PropertyManager.getInstance().getFacebookId();
				if (currentAccessToken != null && currentAccessToken.getUserId().equals(userid)) {
					NetworkManager.getInstnace().loginFacebook(currentAccessToken.getToken(), new OnResultListener<Boolean>() {

						@Override
						public void onSuccess(Boolean result) {
							goMainActivity();
						}

						@Override
						public void onFail(int code) {
							goLoginActivity();
						}
					});
				} else {
					goLoginActivity();
				}
			}
		};
		String facebookId = PropertyManager.getInstance().getFacebookId();
		if (!facebookId.equals("")) {
			LoginManager.getInstance().logInWithReadPermissions(this, null);
		} else {
			goLoginActivity();			
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		accessTokenTracker.stopTracking();
	}

	private void goMainActivity() {
		Toast.makeText(this, "go MainActivity", Toast.LENGTH_SHORT).show();
	}
	
	private void goLoginActivity() {
		Toast.makeText(this, "go LoginActivity", Toast.LENGTH_SHORT).show();
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
