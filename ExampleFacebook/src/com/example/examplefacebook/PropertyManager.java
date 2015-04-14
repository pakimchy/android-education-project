package com.example.examplefacebook;

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
	
	private static final String FIELD_FACEBOOK_ID = "facebookid";
	public void setFacebookId(String facebookId) {
		mEditor.putString(FIELD_FACEBOOK_ID, facebookId);
		mEditor.commit();
	}
	public String getFacebookId() {
		return mPrefs.getString(FIELD_FACEBOOK_ID, "");
	}
}
