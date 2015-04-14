package com.example.examplepreferencesettings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
//	    Intent intent = new Intent(this, SettingActivity.class);
//	    addPreferencesFromIntent(intent);
	}

}
