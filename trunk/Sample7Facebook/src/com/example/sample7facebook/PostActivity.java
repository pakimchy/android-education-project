package com.example.sample7facebook;

import java.util.Arrays;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class PostActivity extends ActionBarActivity {

	CallbackManager callback;
	LoginManager mLM;
	EditText messageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		callback = CallbackManager.Factory.create();
		mLM = LoginManager.getInstance();
		mLM.registerCallback(callback, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				sendPost();
			}
			
			@Override
			public void onError(FacebookException error) {
				
			}
			
			@Override
			public void onCancel() {
				
			}
		});
		messageView = (EditText)findViewById(R.id.edit_message);
		Button btn = (Button)findViewById(R.id.btn_post);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccessToken token = AccessToken.getCurrentAccessToken();
				if (token != null) {
					if (token.getPermissions().contains("publish_actions")) {
						sendPost();
						return;
					}
				}
				mLM.logInWithPublishPermissions(PostActivity.this, Arrays.asList("publish_actions"));
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callback.onActivityResult(requestCode, resultCode, data);
	}
	
	private void sendPost() {
		AccessToken token = AccessToken.getCurrentAccessToken();
		String path = "/me/feed";
		Bundle params = new Bundle();
		String message = messageView.getText().toString();
		params.putString("message", message);
		params.putString("link", "http://developers.facebook.com/docs/android");
        params.putString("picture", "https://raw.github.com/fbsamples/.../iossdk_logo.png");
        params.putString("name", "Hello Facebook");
        params.putString("description", "The 'Hello Facebook' sample  showcases simple …");

		GraphRequest request = new GraphRequest(token, path, params, HttpMethod.POST, new Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				JSONObject object = response.getJSONObject();
				String id = (object == null)?null:object.optString("id");
				Toast.makeText(PostActivity.this, "post : " + id, Toast.LENGTH_SHORT).show();
			}
		});
		request.executeAsync();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post, menu);
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
