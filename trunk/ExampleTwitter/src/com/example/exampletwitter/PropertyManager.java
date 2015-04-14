package com.example.exampletwitter;

import twitter4j.auth.AccessToken;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
		mEditor = mPrefs.edit();
	}
	
	private static final String FIELD_ACCESS_TOKEN = "accessToken";
	private static final String FIELD_ACCESS_SECRET = "accessSecret";
	private static final String FIELD_USER_ID = "userid";
	public AccessToken getAccessToken() {
		String token = mPrefs.getString(FIELD_ACCESS_TOKEN, "");
		if (token.equals("")) {
			return null;
		}
		String secret = mPrefs.getString(FIELD_ACCESS_SECRET, "");
		long userid = mPrefs.getLong(FIELD_USER_ID, 0);
		return new AccessToken(token, secret, userid);
	}

	public void setAccessToken(AccessToken token) {
		mEditor.putString(FIELD_ACCESS_TOKEN, token.getToken());
		mEditor.putString(FIELD_ACCESS_SECRET, token.getTokenSecret());
		mEditor.putLong(FIELD_USER_ID, token.getUserId());
		mEditor.commit();
	}
}
