package com.example.examplesensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	Sensor mRotationSensor;
	SensorManager mSM;
	TextView messageView;
	float[] orientation = new float[3];
	float[] mRotationMatrix = new float[9];

	private static final float THRESHOLD = 1.0f;
	private static final int SHAKE_MIN_COUNT = 3;
	private int mShakeCount = 0;
	private float oldX = 0;
	
	private static final int MESSAGE_SHAKE_TIMEOUT = 1;
	private static final int TIMEOUT_SHAKE = 1000;
	Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_SHAKE_TIMEOUT :
				mShakeCount = 0;
				break;
			}
		}
	};
	Sensor mLinearAccelerationSensor;
	
	public void onShake() {
		Toast.makeText(this, "shake...", Toast.LENGTH_SHORT).show();
	}
	SensorEventListener mListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			switch(event.sensor.getType()) {
			case Sensor.TYPE_ROTATION_VECTOR :
				SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);
				SensorManager.getOrientation(mRotationMatrix, orientation);
				messageView.setText("azimuth : "+orientation[0] + "\npitch : " + orientation[1] + "\nroll : " + orientation[2]);
				break;
			case Sensor.TYPE_LINEAR_ACCELERATION :
				float newX = event.values[0];
				if (Math.abs(newX - oldX) > THRESHOLD && newX * oldX < 0) {
					mShakeCount++;
					mHandler.removeMessages(MESSAGE_SHAKE_TIMEOUT);
					if (mShakeCount > SHAKE_MIN_COUNT) {
						mShakeCount = 0;
						onShake();
					} else {
						mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_SHAKE_TIMEOUT), TIMEOUT_SHAKE);
					}
				}
				oldX = newX;
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.text_message);
		mSM = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mRotationSensor = mSM.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		mLinearAccelerationSensor = mSM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
	}

	@Override
	protected void onStart() {
		super.onStart();
		boolean isRegistered = false;
		if (mRotationSensor != null) {
			mSM.registerListener(mListener, mRotationSensor, SensorManager.SENSOR_DELAY_GAME);
			isRegistered = true;
		} 
		if (mLinearAccelerationSensor != null) {
			mSM.registerListener(mListener, mLinearAccelerationSensor, SensorManager.SENSOR_DELAY_GAME);
			isRegistered = true;
		} 
		if (!isRegistered) {
			messageView.setText("device doesn't have SENSOR");
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (mRotationSensor != null || mLinearAccelerationSensor != null) {
			mSM.unregisterListener(mListener);
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
