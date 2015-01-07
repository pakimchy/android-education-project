package com.example.examplescreenorientation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayOrientation();
	}

	private void displayOrientation() {
		Display display = getWindowManager().getDefaultDisplay();
		int rotate = display.getRotation();
		switch(rotate) {
		case Surface.ROTATION_0 :
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
			break;
		case Surface.ROTATION_90 :
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
			break;
		case Surface.ROTATION_180 :
			Toast.makeText(this, "reverse portrait", Toast.LENGTH_SHORT).show();
			break;
		case Surface.ROTATION_270 :
			Toast.makeText(this, "reverse landscape", Toast.LENGTH_SHORT).show();
			break;			
		}		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		displayOrientation();
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
