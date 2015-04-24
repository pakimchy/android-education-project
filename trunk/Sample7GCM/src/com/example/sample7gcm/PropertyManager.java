package com.example.sample7gcm;

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

	private static final String FIELD_REG_ID = "regid";
	public void setRegistrationId(String regid) {
		mEditor.putString(FIELD_REG_ID, regid);
		mEditor.commit();
	}
	
	public String getRegistrationId() {
		return mPrefs.getString(FIELD_REG_ID, "");
	}
	
	
	
	

}
