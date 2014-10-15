package com.example.sample5sharedpreferences;

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
	
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	private static final String PREF_NAME = "my_prefs";
	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private String userId;
	private static final String FIELD_USER_ID = "userId";
	public void setUserId(String userId) {
		this.userId = userId;
		mEditor.putString(FIELD_USER_ID, userId);
		mEditor.commit();
	}
	
	public String getUserId() {
		if (userId == null) {
			userId = mPrefs.getString(FIELD_USER_ID, "");
		}
		return userId;
	}
	
	private String password;
	private static final String FIELD_PASSWORD = "password";
	public void setPassword(String password) {
		this.password = password;
		mEditor.putString(FIELD_PASSWORD, password);
		mEditor.commit();
	}
	
	public String getPassword() {
		if (password == null) {
			password = mPrefs.getString(FIELD_PASSWORD, "");
		}
		return password;
	}
	
	private Integer age;
	private static final String FIELD_AGE = "age";
	public void setAge(int age) {
		this.age = age;
		mEditor.putInt(FIELD_AGE, age);
		mEditor.commit();
	}
	
	public int getAge() {
		if (age == null) {
			age = mPrefs.getInt(FIELD_AGE, 0);
		}
		return age;
	}
	
}
