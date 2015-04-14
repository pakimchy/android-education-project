package com.example.examplefacebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainFragment extends Fragment {
	CallbackManager callbackManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callbackManager = CallbackManager.Factory.create();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		LoginButton loginButton = (LoginButton)view.findViewById(R.id.login_button);
		loginButton.setFragment(this);
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			
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
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
