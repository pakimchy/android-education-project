package com.example.sample7camera;

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


public class MainActivity extends ActionBarActivity implements SurfaceHolder.Callback {

	SurfaceView preview;
	Camera mCamera;
	int cameraId;
	SurfaceHolder mHolder;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preview = (SurfaceView)findViewById(R.id.surfaceView1);
        preview.getHolder().addCallback(this);
        preview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mCamera = Camera.open();
        cameraId = CameraInfo.CAMERA_FACING_BACK;
        mCamera.setDisplayOrientation(90);
        
        Button btn = (Button)findViewById(R.id.btn_change);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeCamera();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_effect);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setColorEffect();
			}
		});
        
    }
    
    private void setColorEffect() {
    	Camera.Parameters params = mCamera.getParameters();
    	List<String> effects = params.getSupportedColorEffects();
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Color Effect");
    	builder.setIcon(R.drawable.ic_launcher);
    	final String[] items = effects.toArray(new String[effects.size()]);
    	builder.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String effect = items[which];
				Camera.Parameters params = mCamera.getParameters();
				params.setColorEffect(effect);
				mCamera.setParameters(params);
			}
		});
    	builder.create().show();
    }

    
    private void changeCamera() {
    	if (mCamera != null) {
    		mCamera.release();
    		mCamera = null;
    	}
    	cameraId = (cameraId == CameraInfo.CAMERA_FACING_BACK)?CameraInfo.CAMERA_FACING_FRONT : CameraInfo.CAMERA_FACING_BACK;
    	mCamera = Camera.open(cameraId);
    	mCamera.setDisplayOrientation(90);
    	try {
    		if (mHolder != null) {
    			mCamera.setPreviewDisplay(mHolder);
    			mCamera.startPreview();
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		mHolder = holder;
		if (mCamera == null) {
			mCamera = Camera.open(cameraId);
			mCamera.setDisplayOrientation(90);
		}
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		try {
			mCamera.stopPreview();
		} catch(Exception e) {
			
		}
		
		mHolder = holder;
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mHolder = null;
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}
}
