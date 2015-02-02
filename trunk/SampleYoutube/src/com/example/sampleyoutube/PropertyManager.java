package com.example.sampleyoutube;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	private static final String PREF_NAME = "my_prefs";
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String EXIRE_TIME = "exireTime";
	private static final String TOKEN_TYPE = "tokenType";
	private static final String REFRESH_TOKEN = "refreshToken";
	private AccessToken token;
	
	public void setAccessToken(AccessToken token) {
		this.token = token;
		mEditor.putString(ACCESS_TOKEN, token.accessToken);
		mEditor.putString(TOKEN_TYPE, token.tokenType);
		mEditor.putString(REFRESH_TOKEN, token.refreshToken);
		mEditor.putLong(EXIRE_TIME, token.expiresTime);
		mEditor.commit();
	}
	
	public AccessToken getAccessToken() {
		if (token == null) {
			token = new AccessToken();
			token.accessToken = mPrefs.getString(ACCESS_TOKEN, "");
			token.tokenType = mPrefs.getString(TOKEN_TYPE, "");
			token.refreshToken = mPrefs.getString(REFRESH_TOKEN, "");
			token.expiresTime = mPrefs.getLong(EXIRE_TIME, 0);
		}
		return token;
	}
}
