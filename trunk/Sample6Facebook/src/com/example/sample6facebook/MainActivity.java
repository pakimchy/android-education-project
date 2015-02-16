package com.example.sample6facebook;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		
		Button btn = (Button) findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				login(new StatusCallback() {

					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						if (session.isOpened()) {
							String accessToken = session.getAccessToken();
							Request.newMeRequest(session,
									new GraphUserCallback() {

										@Override
										public void onCompleted(GraphUser user,
												Response response) {
											if (user != null) {
												Toast.makeText(
														MainActivity.this,
														"user : "
																+ user.getId(),
														Toast.LENGTH_SHORT)
														.show();
											}
										}
									}).executeAsync();
						}

					}
				});
			}
		});

		btn = (Button) findViewById(R.id.btn_logout);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logout();
			}
		});

		btn = (Button) findViewById(R.id.btn_post);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isLogin()) {
					Session.getActiveSession().addCallback(postCallback);
					postCallback.call(Session.getActiveSession(), null, null);
				} else {
					login(postCallback);
				}

			}
		});
		
		btn = (Button)findViewById(R.id.btn_read);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isLogin()) {
					Session.getActiveSession().addCallback(readCallback);
					readCallback.call(Session.getActiveSession(), null, null);
				} else {
					login(readCallback);
				}
			}
		});
	}

	public static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

	public static final List<String> READ_PERMISSIONS = Arrays.asList("read_stream");
	
	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}
	
	Session.StatusCallback readCallback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			if (session.isOpened()) {
				List<String> permissions = session.getPermissions();
				if (!isSubsetOf(READ_PERMISSIONS, permissions)) {
					session.requestNewReadPermissions(new NewPermissionsRequest(MainActivity.this, READ_PERMISSIONS));
					return;
				}
				Request request = new Request(session, "me/home", null, HttpMethod.GET, new Request.Callback() {
					
					@Override
					public void onCompleted(Response response) {
						if (response.getGraphObject() != null) {
							JSONObject obj = response.getGraphObject().getInnerJSONObject();
							try {
								JSONArray array = obj.getJSONArray("data");
								for (int i = 0; i < array.length(); i++) {
									JSONObject object = array.getJSONObject(i);
									try {
										String story = object.getString("message");
										if (story != null && !story.equals("")) {
											mAdapter.add(story);
										}
									} catch (JSONException e) {
										
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} else {
							Toast.makeText(MainActivity.this, response.getError().toString(), Toast.LENGTH_SHORT).show();
						}
					}
				});
				request.executeAsync();
			}
		}
	};

	Session.StatusCallback postCallback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (session.isOpened()) {
				List<String> permissions = session.getPermissions();
				if (!isSubsetOf(PERMISSIONS, permissions)) {
					session.requestNewPublishPermissions(new NewPermissionsRequest(
							MainActivity.this, PERMISSIONS));
					return;
				}

				Bundle postParams = new Bundle();
				postParams.putString("message", "facebook test message");
				postParams.putString("name", "Education Test for Android");
				postParams.putString("caption", "Test facebook capture.");
				postParams
						.putString(
								"description",
								"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
				postParams.putString("link",
						"https://developers.facebook.com/android");
				postParams
						.putString("picture",
								"https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");
				Request request = new Request(session, "me/feed", postParams,
						HttpMethod.POST, new Request.Callback() {

							@Override
							public void onCompleted(Response response) {
								if (response.getGraphObject() != null) {
									JSONObject obj = response.getGraphObject()
											.getInnerJSONObject();
									try {
										String id = obj.getString("id");
										Toast.makeText(MainActivity.this,
												"id : " + id,
												Toast.LENGTH_SHORT).show();
									} catch (JSONException e) {
										e.printStackTrace();
									}
								} else {
									FacebookRequestError error = response
											.getError();
									Toast.makeText(
											MainActivity.this,
											"error : "
													+ error.getErrorMessage(),
											Toast.LENGTH_SHORT).show();
								}

							}
						});
				request.executeAsync();

			}
		}
	};

	private void login(Session.StatusCallback callback) {
		Session session = Session.getActiveSession();
		
		if (session != null && session.isOpened()) {
			session.addCallback(callback);
			callback.call(session, null, null);
			return;
		}
		
		if (session == null) {
			session = Session.openActiveSessionFromCache(this);
			if (session != null && session.isOpened()) {
				session.addCallback(callback);
				return;
			}
		} 

		Session.openActiveSession(this, true, callback);
	}

	private void logout() {
		if (Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened()) {
			Session.getActiveSession().closeAndClearTokenInformation();
		}
	}

	private boolean isLogin() {
		return Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened();
	}

	LoginButton authButton;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);
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
