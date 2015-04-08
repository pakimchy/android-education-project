package com.example.sample7orientation;

import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout();
	}

	private void setLayout() {
		setContentView(R.layout.activity_main);
		Configuration config = getResources().getConfiguration();
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		}
		
		Button btn = (Button)findViewById(R.id.btn_en);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Resources res = getResources();
				DisplayMetrics dm = res.getDisplayMetrics();
				Configuration config = res.getConfiguration();
				config.locale = Locale.ENGLISH;
				res.updateConfiguration(config, dm);
				setLayout();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_ko);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Resources res = getResources();
				DisplayMetrics dm = res.getDisplayMetrics();
				Configuration config = res.getConfiguration();
				config.locale = Locale.KOREAN;
				res.updateConfiguration(config, dm);
				setLayout();
			}
		});
	}
	String[] mOrientationText = {"portrait" , "landscape", "reverse-portrait", "reverse-landscape"};
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Display display = getWindowManager().getDefaultDisplay();
		int orientation = display.getRotation();
		Toast.makeText(this, "orientation : " + mOrientationText[orientation], Toast.LENGTH_SHORT).show();
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
