package com.example.sample5facebook;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MyFragment extends Fragment {

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
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_main, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button btn = (Button)view.findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(getActivity(), MyFragment.this, true, new StatusCallback() {
					
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {
							Request.newMeRequest(session, new GraphUserCallback() {
								
								@Override
								public void onCompleted(GraphUser user, Response response) {
									if (user != null) {
										Toast.makeText(getActivity(), "id : " + user.getId(), Toast.LENGTH_SHORT).show();
									}
								}
							}).executeAsync();
						}
					}
				});
			}
		});
		
		LoginButton auth = (LoginButton)view.findViewById(R.id.btn_auth);
		auth.setReadPermissions("read_stream");
		auth.setFragment(this);
		
		btn = (Button)view.findViewById(R.id.btn_post);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Session.openActiveSession(getActivity(), MyFragment.this, true,
						new StatusCallback() {

							@Override
							public void call(Session session,
									SessionState state, Exception exception) {
								if (session.isOpened()) {
									List<String> permission = session
											.getPermissions();
									if (!isSubsetOf(PERMISSIONS, permission)) {
										session.requestNewPublishPermissions(new NewPermissionsRequest(
												MyFragment.this, PERMISSIONS));
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
													Toast.makeText(getActivity(), "id : " + id, Toast.LENGTH_SHORT).show();
												} catch (JSONException e) {
													e.printStackTrace();
												}
											} else {
												FacebookRequestError error = response.getError();
												Toast.makeText(getActivity(), "error : " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
											}
											
										}
									});
									request.executeAsync();
								}
							}
						});
				
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
		}
	}
}
