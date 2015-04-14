package com.example.examplesharedpreferences;

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

	private static final String PREFS_NAME = "my_prefs";

	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;

	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}

	private static final String KEY_USER_NAME = "username";
	private String mUserName;
	
	public String getUserName() {
		if (mUserName == null) {
			mUserName = mPrefs.getString(KEY_USER_NAME, "");
		}
		return mUserName;
	}
	
	public void setUserName(String userName) {
		mUserName = userName;
		mEditor.putString(KEY_USER_NAME, userName);
		mEditor.commit();
	}
	
}
