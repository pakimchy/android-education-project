package com.example.sample7autologin;

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
	
	private static final String KEY_USER_ID = "userid";
	public void setUserId(String userid) {
		mEditor.putString(KEY_USER_ID, userid);
		mEditor.commit();
	}
	public String getUserId() {
		return mPrefs.getString(KEY_USER_ID, "");
	}
	
	private static final String KEY_PASSWORD = "password";
	public void setPassword(String password) {
		mEditor.putString(KEY_PASSWORD, password);
		mEditor.commit();
	}
	public String getPassword() {
		return mPrefs.getString(KEY_PASSWORD, "");
	}
}
