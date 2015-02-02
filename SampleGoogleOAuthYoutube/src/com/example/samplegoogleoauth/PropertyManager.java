package com.example.samplegoogleoauth;

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
	
	private static final String FIELD_EMAIL = "email";
	private String email;
	public void setEmail(String email) {
		this.email = email;
		mEditor.putString(FIELD_EMAIL, email);
		mEditor.commit();
	}
	
	public String getEmail() {
		if (email == null) {
			email = mPrefs.getString(FIELD_EMAIL, "");
		}
		return email;
	}
}
