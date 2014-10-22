package com.example.sample5sensor;

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
		
	}
	
	SensorEventListener mListener = new SensorEventListener() {
		float old = SensorManager.GRAVITY_EARTH;
		int count = 0;
		private final static float LIMIT = 2;
		private final static int MAX_COUNT = 3;
		
		private static final int MESSAGE_TIME_OUT = 1;
		private static final int TIME_OUT = 500;
		
		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				switch(msg.what) {
				case MESSAGE_TIME_OUT :
					count = 0;
					break;
				}
			}
		};
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch(event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER :
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				float a = (float)Math.sqrt(x*x + y * y + z * z);
				if (Math.abs(a - old) > LIMIT) {
					count++;
					mHandler.removeMessages(MESSAGE_TIME_OUT);
					if (count > MAX_COUNT) {
						onShake();
						count = 0;
					}
					mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TIME_OUT), TIME_OUT);
				}
				old = a;
				break;
			case Sensor.TYPE_MAGNETIC_FIELD :
				break;
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
