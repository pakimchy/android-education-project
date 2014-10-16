package com.example.sample5videorecording;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	SurfaceView displayView;
	MediaRecorder mRecorder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayView = (SurfaceView)findViewById(R.id.surfaceView1);
		mRecorder = new MediaRecorder();
		Button btn = (Button)findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startRecording();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopRecording();
			}
		});
	}
	
	File outputFile;
	
	private void startRecording() {
		mRecorder.reset();
		mRecorder.setAudioSource(AudioSource.MIC);
		mRecorder.setVideoSource(VideoSource.CAMERA);
		
		mRecorder.setOutputFormat(OutputFormat.MPEG_4);
		
		mRecorder.setAudioEncoder(AudioEncoder.AMR_NB);
		mRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);
		
		File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		outputFile = new File( dir , "my_video_" + (System.currentTimeMillis() / 1000));
		
		mRecorder.setOutputFile(outputFile.getAbsolutePath());
		
		mRecorder.setPreviewDisplay(displayView.getHolder().getSurface());
		
		try {
			mRecorder.prepare();
			mRecorder.start();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	private void stopRecording() {
		if (outputFile != null) {
			mRecorder.stop();
			
			ContentValues values = new ContentValues();
			values.put(Video.Media.TITLE, "my video");
			values.put(Video.Media.MIME_TYPE, "video/mpeg");
			values.put(Video.Media.DATA, outputFile.getAbsolutePath());
			values.put(Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
			values.put(Video.Media.DISPLAY_NAME, "my video");
			Uri uri = getContentResolver().insert(Video.Media.EXTERNAL_CONTENT_URI, values);
			
			if (uri != null) {
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
				sendBroadcast(intent);
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mRecorder.release();
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
