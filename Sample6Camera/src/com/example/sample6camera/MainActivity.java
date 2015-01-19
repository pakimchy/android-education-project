package com.example.sample6camera;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity 
implements SurfaceHolder.Callback {

	SurfaceView displayView;
	Camera mCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayView = (SurfaceView)findViewById(R.id.surfaceView1);
		displayView.getHolder().addCallback(this);
		displayView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//		mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
//		mCamera.setDisplayOrientation(90);
		openCamera();
		Button btn = (Button)findViewById(R.id.btn_effect);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Camera.Parameters params = mCamera.getParameters();
				final List<String> effects = params.getSupportedColorEffects();
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("select color effect");
				String[] items = effects.toArray(new String[effects.size()]);
				builder.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Camera.Parameters params = mCamera.getParameters();
						params.setColorEffect(effects.get(which));
						mCamera.setParameters(params);
					}
				});
				builder.create().show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_change);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openCamera();
				try {
					mCamera.setPreviewDisplay(displayView.getHolder());
					mCamera.startPreview();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		});
	}
	
	int type = Camera.CameraInfo.CAMERA_FACING_FRONT;
	
	public void openCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
		
		type = (type==CameraInfo.CAMERA_FACING_FRONT)?CameraInfo.CAMERA_FACING_BACK:CameraInfo.CAMERA_FACING_FRONT;
		mCamera = Camera.open(type);
		mCamera.setDisplayOrientation(90);
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
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

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
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
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			
		}
		
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			
		}
	}
}
