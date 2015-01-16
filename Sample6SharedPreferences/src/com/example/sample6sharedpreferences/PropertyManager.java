package com.example.sample6sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstnace() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	private static final String PREF_NAME = "mypref";
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private static final String FIELD_USER_NAME = "username";
	private String mUserName;
	
	public String getUserName() {
		if (mUserName == null) {
			mUserName = mPrefs.getString(FIELD_USER_NAME, "");
		}
		return mUserName;
	}
	
	public void setUserName(String name) {
		mUserName = name;
		mEditor.putString(FIELD_USER_NAME, name);
		mEditor.commit();
	}
	
	private static final String FIELD_PASSWORD = "password";
	private String mPassword;
	
	public String getPassword() {
		if (mPassword == null) {
			mPassword = mPrefs.getString(FIELD_PASSWORD, "");
		}
		return mPassword;
	}
	
	public void setPassword(String password) {
		mPassword = password;
		mEditor.putString(FIELD_PASSWORD, password);
		mEditor.commit();
	}
	
	
}
