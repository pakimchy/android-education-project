package com.example.examplefacebook;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	CallbackManager callbackManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		callbackManager = CallbackManager.Factory.create();
		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				getHome();
			}
			
			@Override
			public void onError(FacebookException error) {
				
			}
			
			@Override
			public void onCancel() {
				
			}
		});
		
		initData();
	}
	
	private void initData() {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		if (accessToken != null) {
			if (accessToken.getPermissions().contains("read_stream")) {
				getHome();
				return;
			}
		}
		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("read_stream"));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	private void getHome() {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		String graphPath = "/me/home";
		GraphRequest request = new GraphRequest(accessToken, graphPath, null, HttpMethod.GET, new GraphRequest.Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				JSONObject obj = response.getJSONObject();
				JSONArray array = (obj==null)?null:obj.optJSONArray("data");
				mAdapter.clear();
				if (array != null) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject story = array.optJSONObject(i);
						String message = (story == null)?null:story.optString("message");
						if (message != null && !message.equals("")) {
							mAdapter.add(message);
						}
					}
				}
			}
		});
		request.executeAsync();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
