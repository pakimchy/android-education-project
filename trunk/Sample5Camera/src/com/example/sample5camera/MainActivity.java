package com.example.sample5camera;

import java.io.IOException;

import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements
		SurfaceHolder.Callback {

	SurfaceView previewDisplay;
	Camera mCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		previewDisplay = (SurfaceView)findViewById(R.id.surfaceView1);
		previewDisplay.getHolder().addCallback(this);
		previewDisplay.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		Button btn = (Button)findViewById(R.id.btn_change_camera);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int type = (cameraType == TYPE_FRONT)?TYPE_BACK:TYPE_FRONT;
				changeCamera(type);
			}
		});
		changeCamera(TYPE_BACK);
		
	}
	
	private static final int TYPE_BACK = 1;
	private static final int TYPE_FRONT = 2;
	int cameraType = -1;
	SurfaceHolder mHolder = null;
	
	private void changeCamera(int type) {
		if (cameraType != type) {
			cameraType = type;
			if (mCamera != null) {
				mCamera.release();
				mCamera = null;
			}
			
			int cameraId = (type == TYPE_FRONT)?Camera.CameraInfo.CAMERA_FACING_FRONT:Camera.CameraInfo.CAMERA_FACING_BACK;
			mCamera = Camera.open(cameraId);
			int orientation = getResources().getConfiguration().orientation; 
			if ( orientation == Configuration.ORIENTATION_PORTRAIT) {
				mCamera.setDisplayOrientation(90);
			} 
			if (mHolder != null) {
				try {
					mCamera.setPreviewDisplay(mHolder);
					mCamera.startPreview();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCamera.release();
		mCamera = null;
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

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (mCamera == null) return;
		try {
			mHolder = holder;
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mCamera == null) return;
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			
		}
		
		try {
			mHolder = holder;
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera == null) return;
		mHolder = null;
		mCamera.stopPreview();
	}
}
