package com.example.sample7facebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sample7facebook.NetworkManager.OnResultListener;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class SplashActivity extends ActionBarActivity {

	CallbackManager callback;
	AccessTokenTracker tracker;
	LoginManager mLM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		callback = CallbackManager.Factory.create();
		mLM = LoginManager.getInstance();
		mLM.registerCallback(callback, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				
			}
			
			@Override
			public void onError(FacebookException error) {
				
			}
			
			@Override
			public void onCancel() {
				
			}
		});
		tracker = new AccessTokenTracker() {
			
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
					AccessToken currentAccessToken) {
				if (currentAccessToken != null) {
					processFacebookLogin();
				}
			}
		};
		String facebookId = PropertyManager.getInstance().getFacebookId();
		if (!facebookId.equals("")) {
			mLM.logInWithReadPermissions(this, null);
		} else {
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					startActivity(new Intent(SplashActivity.this, OtherActivity.class));
				}
			}, 1000);
		}
	}
	
	Handler mHandler = new Handler(Looper.getMainLooper());
	
	private void processFacebookLogin() {
		AccessToken token = AccessToken.getCurrentAccessToken();
		String fid = PropertyManager.getInstance().getFacebookId();
		String uid = token.getUserId();
		if (fid.equals(uid)) {
			NetworkManager.getInstance().loginFacebook(this, token.getToken(), new OnResultListener<String>() {
				
				@Override
				public void onSuccess(String result) {
					if (result.equals("success")) {
						startActivity(new Intent(SplashActivity.this, MainActivity.class));
						finish();
					} else if (result.equals("notregister")){
						// system error...
						// hacking...
					} else if (result.equals("fail")) {
						mLM.logOut();
						startActivity(new Intent(SplashActivity.this, OtherActivity.class));
						finish();
					}
				}
				
				@Override
				public void onError(int code) {
					
				}
			});
		} else {
			Toast.makeText(this, "user facebook id changed...", Toast.LENGTH_SHORT).show();
			mLM.logOut();
			startActivity(new Intent(this, OtherActivity.class));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callback.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tracker.stopTracking();
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
