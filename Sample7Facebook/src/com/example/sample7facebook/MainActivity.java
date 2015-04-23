package com.example.sample7facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends ActionBarActivity {

	LoginButton loginButton;
	CallbackManager callback;
	Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginButton = (LoginButton)findViewById(R.id.login);
		callback = CallbackManager.Factory.create();
		loginButton.registerCallback(callback, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				Toast.makeText(MainActivity.this, "login...", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(FacebookException error) {
				
			}
			
			@Override
			public void onCancel() {
				
			}
		});
		
		Button btn = (Button)findViewById(R.id.btn_activity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, OtherActivity.class));
			}
		});
		
		btnLogin = (Button)findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccessToken token = AccessToken.getCurrentAccessToken();
				if (token == null) {
					login();
				} else {
					logout();
				}
			}
		});
		setButtonLable();
		mLoginManager = LoginManager.getInstance();
		tracker = new AccessTokenTracker() {
			
			@Override
			protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
					AccessToken currentAccessToken) {
				setButtonLable();
			}
		};
		
		btn = (Button)findViewById(R.id.btn_post_activity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PostActivity.class));
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tracker.stopTracking();
	}
	
	AccessTokenTracker tracker;
	
	private void setButtonLable() {
		AccessToken token = AccessToken.getCurrentAccessToken();
		if (token == null) {
			btnLogin.setText("login");
		} else {
			btnLogin.setText("logout");
		}
	}

	LoginManager mLoginManager;

	private void logout() {
		mLoginManager.logOut();
	}
	
	private void login() {
		mLoginManager.registerCallback(callback, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				Toast.makeText(MainActivity.this, "button login...", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancel() {
				Toast.makeText(MainActivity.this, "button cancel...", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(FacebookException error) {
				
			}
		});
		mLoginManager.logInWithReadPermissions(this, null);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callback.onActivityResult(requestCode, resultCode, data);
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
