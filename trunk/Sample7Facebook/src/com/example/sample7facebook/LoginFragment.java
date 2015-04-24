package com.example.sample7facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sample7facebook.NetworkManager.OnResultListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginFragment extends Fragment {

	CallbackManager callback;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = CallbackManager.Factory.create();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
		LoginButton btn = (LoginButton)view.findViewById(R.id.login);
		btn.setFragment(this);
		btn.registerCallback(callback, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				Toast.makeText(getActivity(), "fragment login...", Toast.LENGTH_SHORT).show();
				AccessToken token = AccessToken.getCurrentAccessToken();
				NetworkManager.getInstance().loginFacebook(getActivity(), token.getToken(), new OnResultListener<String>() {
					
					@Override
					public void onSuccess(String result) {
						if (result.equals("success")) {
							AccessToken token = AccessToken.getCurrentAccessToken();
							PropertyManager.getInstance().setFacebookId(token.getUserId());
							startActivity(new Intent(getActivity(), MainActivity.class));
							getActivity().finish();
						} else if (result.equals("notregistered")) {
							// signup activity....
							startActivity(new Intent(getActivity(), SignupFacebookActivity.class));
							getActivity().finish();
						} else {
							Toast.makeText(getActivity(), "fail...", Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onError(int code) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			
			@Override
			public void onError(FacebookException error) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callback.onActivityResult(requestCode, resultCode, data);
	}
}
