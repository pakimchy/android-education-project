package com.example.sample7gcm;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends ActionBarActivity {

	private static final String SENDER_ID = "972646024433";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkAndRegister();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				checkAndRegister();
			} else {
				finish();
			}
		}
	}

	private void checkAndRegister() {
		if (checkPlayServices()) {
			String regid = PropertyManager.getInstance().getRegistrationId();
			if (regid.equals("")) {
				registerInBackground();
			} else {
				doRealStart();
			}
		}
	}

	private void doRealStart() {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				String regid = PropertyManager.getInstance().getRegistrationId();
				ServerUtilities.register(MainActivity.this, regid);
				return null;
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				Toast.makeText(MainActivity.this, "registered server", Toast.LENGTH_SHORT).show();
			}
		}.execute();
	}
	
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST);
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						finish();
					}
				});
				dialog.show();
			} else {
				finish();
			}
			return false;
		}
		return true;
	}
	

	private void registerInBackground() {
		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				try {
					GoogleCloudMessaging gcm = GoogleCloudMessaging
							.getInstance(MainActivity.this);
					String regid = gcm.register(SENDER_ID);
					PropertyManager.getInstance().setRegistrationId(regid);
					return regid;
				} catch (IOException ex) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(String msg) {
				doRealStart();
			}
		}.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
