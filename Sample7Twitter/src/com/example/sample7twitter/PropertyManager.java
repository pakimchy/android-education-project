package com.example.sample7twitter;

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
	
	private static final String KEY_TOKEN = "token";
	private static final String KEY_SECRET = "secret";
	private static final String KEY_USER_ID = "userid";
	
	public void setAccessToken(AccessToken token) {
		mEditor.putString(KEY_TOKEN, token.getToken());
		mEditor.putString(KEY_SECRET, token.getTokenSecret());
		mEditor.putLong(KEY_USER_ID, token.getUserId());
		mEditor.commit();
	}
	
	public AccessToken getAccessToken() {
		String token = mPrefs.getString(KEY_TOKEN, "");
		if (!token.equals("")) {
			String secret = mPrefs.getString(KEY_SECRET, "");
			long userid = mPrefs.getLong(KEY_USER_ID, 0);
			return new AccessToken(token, secret, userid);
		}
		return null;
	}
}
