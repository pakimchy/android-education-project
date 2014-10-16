package com.example.sample5camera;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images;
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
		
		btn = (Button)findViewById(R.id.btn_effect);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mCamera != null) {
					Camera.Parameters params = mCamera.getParameters();
					List<String> effectlist = params.getSupportedColorEffects();
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Select Effect");
					builder.setIcon(R.drawable.ic_launcher);
					final String[] items = new String[effectlist.size()];
					effectlist.toArray(items);
					builder.setItems(items, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Camera.Parameters params = mCamera.getParameters();
							params.setColorEffect(items[which]);
							mCamera.setParameters(params);
						}
					});
					builder.create().show();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_capture);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mCamera != null) {
					mHandler.removeCallbacks(takePictureRunnable);
					picture_count = 3;
					mHandler.postDelayed(takePictureRunnable, 5000);
				}
			}
		});
		changeCamera(TYPE_BACK);
		
	}
	
	
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 2;
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
			Images.Media.insertImage(getContentResolver(), bitmap, "Camera Image", "my camera picture");
			
//			File f = new File("/a/b/c.jpg");
//			FileOutputStream fos = new FileOutputStream(f);
//			fos.write(data);
//			fos.close();
		}
	};
	Handler mHandler = new Handler();

	int picture_count = 3;
	int picture_interval = 1000;
	
	Runnable takePictureRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (mCamera != null && picture_count > 0) {
				mCamera.takePicture(null, null, pictureCallback);
				picture_count--;
				mHandler.postDelayed(this, picture_interval);
			}
		}
	};
	
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
