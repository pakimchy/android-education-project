package com.example.sample6sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	SensorManager mSM;
	Sensor mAcc;
	Sensor mMag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSM = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		
		mAcc = mSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMag = mSM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
	}

	private void onShake() {
		Toast.makeText(this, "Shake....", Toast.LENGTH_SHORT).show();
	}
	
	Handler mHandler = new Handler();
	
	SensorEventListener mListener = new SensorEventListener() {
		
		double oldAcc = SensorManager.GRAVITY_EARTH;
		private final static double DELTA = 1.0;
		int shakeCount = 0;
		private final static int MAX_COUNT = 3;
		private final static int INTERVAL = 500;
		
		Runnable resetRunnable = new Runnable() {
			
			@Override
			public void run() {
				shakeCount = 0;
			}
		};
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch(event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER :
				double x = event.values[0];
				double y = event.values[1];
				double z = event.values[2];
				double acc = Math.sqrt(x*x + y*y + z*z);
				if (Math.abs(acc - oldAcc) > DELTA) {
					shakeCount++;
					if (shakeCount > MAX_COUNT) {
						onShake();
						shakeCount = 0;
					} else {
						mHandler.removeCallbacks(resetRunnable);
						mHandler.postDelayed(resetRunnable, INTERVAL);
					}
				}
				oldAcc = acc;
				break;
			case Sensor.TYPE_MAGNETIC_FIELD :
				
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	};
	
	@Override
	protected void onStart() {
		super.onStart();
		mSM.registerListener(mListener, mAcc, SensorManager.SENSOR_DELAY_GAME);
		mSM.registerListener(mListener, mMag, SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mSM.unregisterListener(mListener);
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
