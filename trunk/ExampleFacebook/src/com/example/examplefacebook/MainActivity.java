package com.example.examplefacebook;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.examplefacebook.NetworkManager.OnResultListener;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends ActionBarActivity {

	CallbackManager callbackManager;
	AccessTokenTracker accessTokenTracker;
	Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		callbackManager = CallbackManager.Factory.create();
		LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				Toast.makeText(MainActivity.this, "login Success", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(FacebookException error) {
				Toast.makeText(MainActivity.this, "login error", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onCancel() {
				Toast.makeText(MainActivity.this, "login cancel", Toast.LENGTH_SHORT).show();
			}
		});
		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				Toast.makeText(MainActivity.this, "login Success", Toast.LENGTH_SHORT).show();
				final AccessToken accessToken = AccessToken.getCurrentAccessToken();
				String token = accessToken.getToken();
				NetworkManager.getInstnace().loginFacebook(token, new OnResultListener<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						Toast.makeText(MainActivity.this, "service login success...", Toast.LENGTH_SHORT).show();
						PropertyManager.getInstance().setFacebookId(accessToken.getUserId());
					}
					
					@Override
					public void onFail(int code) {
						
					}
				});
			}
			
			@Override
			public void onError(FacebookException error) {
				Toast.makeText(MainActivity.this, "login error", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onCancel() {
				Toast.makeText(MainActivity.this, "login cancel", Toast.LENGTH_SHORT).show();
			}
		});
		
		btnLogin = (Button)findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccessToken accessToken = AccessToken.getCurrentAccessToken();
				if (accessToken == null) {
					LoginManager.getInstance().setDefaultAudience(DefaultAudience.FRIENDS);
					LoginManager.getInstance().setLoginBehavior(LoginBehavior.SSO_WITH_FALLBACK);
					LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, new ArrayList<String>());
				} else {
					LoginManager.getInstance().logOut();
				}
			}
		});
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		if (accessToken != null) {
			btnLogin.setText("Logout");
		} else {
			btnLogin.setText("Login");
		}
		accessTokenTracker = new AccessTokenTracker() {
	         @Override
	         protected void onCurrentAccessTokenChanged(
	                 AccessToken oldAccessToken,
	                 AccessToken currentAccessToken) {
	        	 if (currentAccessToken != null) {
	        		 Toast.makeText(MainActivity.this, "login : " + currentAccessToken.getToken(), Toast.LENGTH_SHORT).show();
	        		 btnLogin.setText("Logout");
	        	 } else {
	        		 Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
	        		 btnLogin.setText("Login");
	        	 }
	         }
	    };
	    
	    Button btn = (Button)findViewById(R.id.btn_post);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PostActivity.class));
			}
		});
	    
	    btn = (Button)findViewById(R.id.btn_home);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, HomeActivity.class));
			}
		});
	    
	    btn = (Button)findViewById(R.id.btn_splash);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, SplashActivity.class));
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		accessTokenTracker.stopTracking();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
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
