package com.example.examplepreferencesettings;

import java.util.List;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class PreferenceHeaderActivity extends PreferenceActivity {

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}
	
	protected boolean isValidFragment(String fragmentName) {
		return true;
	}
}
