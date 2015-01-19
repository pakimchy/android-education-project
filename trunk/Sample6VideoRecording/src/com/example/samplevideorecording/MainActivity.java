package com.example.samplevideorecording;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;

public class MainActivity extends ActionBarActivity implements
		SurfaceHolder.Callback {

	MediaRecorder mRecorder;
	SurfaceView displayView;
	SurfaceHolder mSurfaceHolder;

	public static final int STATE_NOT_RECORDING = 0;
	public static final int STATE_RECORDING = 1;
	public static final int STATE_RELEASED = 2;
	int mState = STATE_NOT_RECORDING;
	
	Gallery gallery;
	ImageAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayView = (SurfaceView) findViewById(R.id.surfaceView1);
		displayView.getHolder().addCallback(this);
		gallery = (Gallery)findViewById(R.id.gallery1);
		mAdapter = new ImageAdapter(this);
		gallery.setAdapter(mAdapter);
		
		mRecorder = new MediaRecorder();

		Button btn = (Button) findViewById(R.id.btn_recording);
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
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
			mState = STATE_RELEASED;
		}
	}

	File mSavedFile;

	@Override
	protected void onPause() {
		super.onPause();
		if (mState == STATE_RECORDING) {
			stopRecording();
		}
	}
	
	private void startRecording() {
		// if (mState == STATE_RECORDING) {
		// stopRecording();
		// }

		if (mState == STATE_NOT_RECORDING) {
			mRecorder.reset();
			mRecorder.setAudioSource(AudioSource.MIC);
			mRecorder.setVideoSource(VideoSource.CAMERA);

			mRecorder.setOutputFormat(OutputFormat.MPEG_4);

			mRecorder.setAudioEncoder(AudioEncoder.AMR_NB);
			mRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);

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
				mState = STATE_RECORDING;
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void stopRecording() {
		if (mState == STATE_RECORDING) {
			mRecorder.stop();

			addToMediaStore();
			
			Bitmap thumb = ThumbnailUtils.createVideoThumbnail(mSavedFile.getAbsolutePath(), Thumbnails.MINI_KIND);
			mAdapter.add(thumb);
			
			mState = STATE_NOT_RECORDING;
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
		mSurfaceHolder = holder;
		if (mState == STATE_RECORDING) {
			stopRecording();
			startRecording();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mSurfaceHolder = holder;
		if (mState == STATE_RECORDING) {
			stopRecording();
			startRecording();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceHolder = null;
		if (mState == STATE_RECORDING) {
			stopRecording();
		}
	}
}
