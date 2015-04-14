package com.example.examplemonitoringscreencapture;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	FileObserver mObserver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		File screenShotFile = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Screenshots");
		if (!screenShotFile.exists()) {
			screenShotFile.mkdirs();
		}
		mObserver = new ScreenCaptureObserver(screenShotFile.getAbsolutePath());
		Button btn = (Button) findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mObserver.startWatching();
			}
		});

		btn = (Button) findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mObserver.stopWatching();
			}
		});
	}

	class ScreenCaptureObserver extends FileObserver {

		public ScreenCaptureObserver(String path) {
			super(path);
		}

		@Override
		public void onEvent(int event, String path) {
			switch (event) {
			case FileObserver.CREATE:
				Toast.makeText(MainActivity.this, "create : " + path,
						Toast.LENGTH_SHORT).show();
				break;
			case FileObserver.CLOSE_WRITE:
				Toast.makeText(MainActivity.this, "capture end : " + path,
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

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
