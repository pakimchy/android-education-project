package com.example.sample5facebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Request.GraphUserListCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends ActionBarActivity {

	LoginButton authButton;

	public static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true,
						new StatusCallback() {

							@Override
							public void call(Session session,
									SessionState state, Exception exception) {
								if (session.isOpened()) {
									String accessToken = session
											.getAccessToken();
									Request.newMeRequest(session,
											new GraphUserCallback() {

												@Override
												public void onCompleted(
														GraphUser user,
														Response response) {
													if (user != null) {
														Toast.makeText(
																MainActivity.this,
																"my id : "
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

		authButton = (LoginButton) findViewById(R.id.btn_auth);
		authButton.setReadPermissions("email");
		authButton.setSessionStatusCallback(new StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {
					Toast.makeText(MainActivity.this, "login...",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn = (Button) findViewById(R.id.btn_post);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true,
						new StatusCallback() {

							@Override
							public void call(Session session,
									SessionState state, Exception exception) {
								if (session.isOpened()) {
									List<String> permission = session
											.getPermissions();
									if (!isSubsetOf(PERMISSIONS, permission)) {
										session.requestNewPublishPermissions(new NewPermissionsRequest(
												MainActivity.this, PERMISSIONS));
										return;
									}
									
									Bundle postParams = new Bundle();
									postParams.putString("message",
											"facebook test message");
									postParams.putString("name",
											"Education Test for Android");
									postParams.putString("caption",
											"Test facebook capture.");
									postParams
											.putString(
													"description",
													"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
									postParams
											.putString("link",
													"https://developers.facebook.com/android");
									postParams
											.putString("picture",
													"https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");
									Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, new Request.Callback() {
										
										@Override
										public void onCompleted(Response response) {
											if (response.getGraphObject() != null) {
												JSONObject obj = response.getGraphObject().getInnerJSONObject();
												try {
													String id = obj.getString("id");
													Toast.makeText(MainActivity.this, "id : " + id, Toast.LENGTH_SHORT).show();
												} catch (JSONException e) {
													e.printStackTrace();
												}
											} else {
												FacebookRequestError error = response.getError();
												Toast.makeText(MainActivity.this, "error : " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
											}
											
										}
									});
									request.executeAsync();
								}
							}
						});
			}
		});
		
		btn = (Button)findViewById(R.id.btn_picture_upload);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Cursor c = getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, new String[]{Images.Media.DATA}, null, null, null);
				String path = null;
				if (c.moveToNext()) {
					path = c.getString(c.getColumnIndex(Images.Media.DATA));
				}
				c.close();
				if (path != null) {
					Session session = Session.getActiveSession();
					final File file = new File(path);
					if (session == null) {
						session = Session.openActiveSessionFromCache(MainActivity.this);
					}
					if (session != null && session.isOpened()) {
						session.addCallback(new StatusCallback() {
							
							@Override
							public void call(Session session, SessionState state, Exception exception) {
								uploadImage(session, file);
							}
						});
						uploadImage(session, file);
					} else {
						Toast.makeText(MainActivity.this, "facebook not login", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(MainActivity.this, "no photo", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_read);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session session = Session.getActiveSession();
				if (session == null) {
					session = Session.openActiveSessionFromCache(MainActivity.this);
				}
				if (session != null && session.isOpened()) {
					session.addCallback(new StatusCallback() {
						
						@Override
						public void call(Session session, SessionState state, Exception exception) {
							readPost(session);
						}
					});
					readPost(session);
				} else {
					Toast.makeText(MainActivity.this, "facebook not login", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_friend_list);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(MainActivity.this, true, new StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							Request.newMyFriendsRequest(session, new GraphUserListCallback() {
								
								@Override
								public void onCompleted(List<GraphUser> users, Response response) {
									if (users != null) {
										Toast.makeText(MainActivity.this, "users : " + users.toString(), Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(MainActivity.this, "error ", Toast.LENGTH_SHORT).show();
									}
								}
							}).executeAsync();
						}
					}
				});
			}
		});
		
		btn = (Button)findViewById(R.id.btn_show_fragment);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyFragmentActivity.class);
				startActivity(i);
			}
		});
	}
	
	public static final List<String> READ_PERMISSIONS = Arrays
			.asList("read_stream");
	private void readPost(Session session) {
		List<String> permissions = session.getPermissions();
		if (!isSubsetOf(READ_PERMISSIONS, permissions)) {
			session.requestNewReadPermissions(new NewPermissionsRequest(this, READ_PERMISSIONS));
			return;
		}
		Bundle params = new Bundle();
//		params.putString("until","" + (System.currentTimeMillis() / 1000));
		Request request = new Request(session, "me/feed", params, HttpMethod.GET, new Callback() {
			
			@Override
			public void onCompleted(Response response) {
				if (response.getGraphObject() != null) {
					JSONObject obj = response.getGraphObject().getInnerJSONObject();
					Toast.makeText(MainActivity.this, "list : " + obj.toString(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "error : " + response.getError(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		request.executeAsync();
	}
	private void uploadImage(Session session, File file) {
		List<String> permissions = session.getPermissions();
		if (!isSubsetOf(PERMISSIONS, permissions)) {
			session.requestNewPublishPermissions(new NewPermissionsRequest(this, PERMISSIONS));
			return;
		}
		
		try {
			Request.newUploadPhotoRequest(session, file, new Callback() {
				
				@Override
				public void onCompleted(Response response) {
					if (response.getGraphObject() != null) {
						JSONObject obj = response.getGraphObject().getInnerJSONObject();
						try {
							String id = obj.getString("id");
							Toast.makeText(MainActivity.this, "id : " + id, Toast.LENGTH_SHORT).show();
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					} else {
						Toast.makeText(MainActivity.this, "error : " + response.getError(), Toast.LENGTH_SHORT).show();
					}
				}
			}).executeAsync();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
