package com.example.examplexmpp;

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
	
	private static final String FIELD_USERID = "userid";
	public String getUserId() {
		return mPrefs.getString(FIELD_USERID, "");
	}
	public void setUserId(String userId) {
		mEditor.putString(FIELD_USERID, userId);
		mEditor.commit();
	}
	
	private static final String FIELD_PASSWORD = "password";
	public String getPassword() {
		return mPrefs.getString(FIELD_PASSWORD, "");
	}
	public void setPassword(String password) {
		mEditor.putString(FIELD_PASSWORD, password);
		mEditor.commit();
	}
}
