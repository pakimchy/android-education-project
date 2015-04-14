package com.example.examplefacebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
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
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class PostActivity extends ActionBarActivity {
	EditText messageView;
	CallbackManager callbackManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		messageView = (EditText)findViewById(R.id.edit_message);
		callbackManager = CallbackManager.Factory.create();
		LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				sendMessage();
			}

			@Override
			public void onCancel() {
				
			}

			@Override
			public void onError(FacebookException error) {
				
			}
		});
		
		Button btn = (Button)findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccessToken accessToken = AccessToken.getCurrentAccessToken();
				if (accessToken != null) {
					if (accessToken.getPermissions().contains("publish_actions")) {
						sendMessage();
						return;
					} 
				} 
				LoginManager.getInstance().logInWithPublishPermissions(PostActivity.this, Arrays.asList("publish_actions"));
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	private void sendMessage() {
		String message = messageView.getText().toString();
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		String graphPath = "/me/feed";
		Bundle parameters = new Bundle();
		parameters.putString("message",message);
		parameters.putString("link", "http://developers.facebook.com/docs/android");
		parameters.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");
		parameters.putString("name", "Hello Facebook");
		parameters.putString("description", "The 'Hello Facebook' sample  showcases simple Facebook integration");
		GraphRequest request = new GraphRequest(accessToken, graphPath, parameters, HttpMethod.POST, new GraphRequest.Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				JSONObject data = response.getJSONObject();
				String id = (data == null)?null:data.optString("id");
				Toast.makeText(PostActivity.this, "post object id : " + id, Toast.LENGTH_SHORT).show();
			}
		});
		request.executeAsync();
	}
	
	private void sendPhoto(Bitmap photo) {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		String graphPath = "me/photos";
		Bundle parameters = new Bundle();
		parameters.putParcelable("picture", photo);
		GraphRequest request = new GraphRequest(accessToken, graphPath, parameters, HttpMethod.POST, new GraphRequest.Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				
			}
		});
		request.executeAsync();
	}

	private void sendPhoto(File photo) throws FileNotFoundException {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		String graphPath = "me/photos";
		Bundle parameters = new Bundle();
		ParcelFileDescriptor fd = ParcelFileDescriptor.open(photo, ParcelFileDescriptor.MODE_READ_ONLY);
		parameters.putParcelable("picture", fd);
		GraphRequest request = new GraphRequest(accessToken, graphPath, parameters, HttpMethod.POST, new GraphRequest.Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				
			}
		});
		request.executeAsync();
	}
	
	private void sendPhoto(Uri uri) {
		if (!"content".equalsIgnoreCase(uri.getScheme())) {
			return;
		}
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		String graphPath = "me/photos";
		Bundle parameters = new Bundle();
		parameters.putParcelable("picture", uri);
		GraphRequest request = new GraphRequest(accessToken, graphPath, parameters, HttpMethod.POST, new GraphRequest.Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				
			}
		});
		request.executeAsync();
	}
	
	private void sendVideo(File file) throws FileNotFoundException {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		String graphPath = "me/videos";
		Bundle parameters = new Bundle();
		ParcelFileDescriptor fd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
		parameters.putParcelable(file.getName(), fd);
		GraphRequest request = new GraphRequest(accessToken, graphPath, parameters, HttpMethod.POST, new GraphRequest.Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				
			}
		});
		request.executeAsync();
	}
	
	private void sendVideo(Uri uri) {
		if (!"content".equalsIgnoreCase(uri.getScheme())) {
			return;
		}
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		String graphPath = "me/videos";
		Bundle parameters = new Bundle();
		parameters.putParcelable("picture", uri);
		GraphRequest request = new GraphRequest(accessToken, graphPath, parameters, HttpMethod.POST, new GraphRequest.Callback() {
			
			@Override
			public void onCompleted(GraphResponse response) {
				
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
