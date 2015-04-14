package com.example.examplepreferencesettings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
//		Intent intent = new Intent(getActivity(), SettingActivity.class);
//		addPreferencesFromIntent(intent);
	}
}
