package com.example.examplevideorecording;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;

public class MainActivity extends ActionBarActivity implements
		SurfaceHolder.Callback {

	MediaRecorder mRecorder;
	SurfaceView mPreview;
	SurfaceHolder mSurfaceHolder;
	Gallery gallery;
	ImageAdapter mAdapter;
	boolean isRecording = false;
	File mSavedFile;
	boolean useProfile = false;
	Camera mCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPreview = (SurfaceView) findViewById(R.id.surface_preview);
		mPreview.getHolder().addCallback(this);
		mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		gallery = (Gallery) findViewById(R.id.gallery_items);
		mAdapter = new ImageAdapter(this);
		gallery.setAdapter(mAdapter);
		Button btn = (Button) findViewById(R.id.btn_record);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startRecording();
			}
		});

		btn = (Button) findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stopRecording();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	public static int setCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
		return result;
	}

	private void startRecording() {
		if (!isRecording) {
			isRecording = true;
			mRecorder = new MediaRecorder();

			if (mCamera != null) {
				mCamera.release();
			}
			mCamera = Camera.open();
			setCameraDisplayOrientation(this, CameraInfo.CAMERA_FACING_BACK, mCamera);
			mCamera.unlock();
			mRecorder.setCamera(mCamera);

			mRecorder.setAudioSource(AudioSource.MIC);
			mRecorder.setVideoSource(VideoSource.CAMERA);

			if (useProfile) {
				CamcorderProfile profile = CamcorderProfile
						.get(CamcorderProfile.QUALITY_LOW);
				mRecorder.setProfile(profile);
			} else {
				mRecorder.setOutputFormat(OutputFormat.MPEG_4);
				mRecorder.setAudioEncoder(AudioEncoder.AMR_NB);
				mRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);
				mRecorder.setVideoSize(320, 240);
			}
			mRecorder.setMaxDuration(10 * 1000);
			mRecorder.setMaxFileSize(20 * 1024 * 1024);

			File dir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			mSavedFile = new File(dir, "my_video_" + System.currentTimeMillis()
					+ ".mpeg");

			mRecorder.setOutputFile(mSavedFile.getAbsolutePath());

			if (mSurfaceHolder != null) {
				mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
			}

			try {
				mRecorder.prepare();
				mRecorder.start();
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void stopRecording() {
		if (isRecording) {
			isRecording = false;
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			mCamera.release();
			mCamera = null;

			addToMediaStore();

			Bitmap thumb = ThumbnailUtils.createVideoThumbnail(
					mSavedFile.getAbsolutePath(), Thumbnails.MINI_KIND);
			mAdapter.add(thumb);

		}
	}

	private void addToMediaStore() {
		ContentValues values = new ContentValues();
		values.put(Video.Media.TITLE, "my video");
		values.put(Video.Media.DESCRIPTION, "test video");
		values.put(Video.Media.DATA, mSavedFile.getAbsolutePath());
		values.put(Video.Media.MIME_TYPE, "video/mpeg");
		values.put(Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);

		Uri uri = getContentResolver().insert(Video.Media.EXTERNAL_CONTENT_URI,
				values);
		if (uri != null) {
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		mSurfaceHolder = holder;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceHolder = null;
		if (isRecording) {
			stopRecording();
		}
	}
}
