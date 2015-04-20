package com.example.sample7videorecorder;

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
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;


public class MainActivity extends ActionBarActivity implements SurfaceHolder.Callback{

	MediaRecorder mRecorder;
	SurfaceView preview;
	boolean isRecording = false;
	SurfaceHolder mHolder;
	Gallery gallery;
	MyAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preview = (SurfaceView)findViewById(R.id.surfaceView1);
        preview.getHolder().addCallback(this);
        preview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        gallery = (Gallery)findViewById(R.id.gallery1);
        mAdapter = new MyAdapter();
        gallery.setAdapter(mAdapter);
        Button btn = (Button)findViewById(R.id.btn_record);
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
    
    File mSaveFile;
    
    private void startRecording() {
    	if (!isRecording) {
    		mRecorder = new MediaRecorder();
    		mRecorder.setAudioSource(AudioSource.MIC);
    		mRecorder.setVideoSource(VideoSource.CAMERA);
    		
    		mRecorder.setOutputFormat(OutputFormat.MPEG_4);
    		
    		mRecorder.setAudioEncoder(AudioEncoder.AMR_NB);
    		mRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);
    		mRecorder.setVideoSize(320, 240);
    		
    		File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    		if (!dir.exists()) {
    			dir.mkdirs();
    		}
    		
    		mSaveFile = new File(dir, "my_video_" + System.currentTimeMillis() + ".mpeg");
    		mRecorder.setOutputFile(mSaveFile.getAbsolutePath());
    		
    		if (mHolder != null) {
    			mRecorder.setPreviewDisplay(mHolder.getSurface());
    		}
    		
    		try {
				mRecorder.prepare();
				mRecorder.start();
				isRecording = true;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		
    	}
    }
    
    private void stopRecording() {
    	if (isRecording) {
    		mRecorder.stop();
    		mRecorder.release();
    		mRecorder = null;
    		
    		addToMediaStore();
    		isRecording = false;
    		
        	Bitmap bm = ThumbnailUtils.createVideoThumbnail(mSaveFile.getAbsolutePath(), Video.Thumbnails.MINI_KIND);
        	mAdapter.add(bm);
    	}
    }
    
    private void addToMediaStore() {
    	ContentValues values = new ContentValues();
    	values.put(Video.Media.TITLE, "my video");
    	values.put(Video.Media.DESCRIPTION, "my test video");
    	values.put(Video.Media.DATA, mSaveFile.getAbsolutePath());
    	values.put(Video.Media.MIME_TYPE, "video/mpeg");
    	values.put(Video.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
    	
    	Uri uri = getContentResolver().insert(Video.Media.EXTERNAL_CONTENT_URI, values);
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
		mHolder = holder;
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mHolder = holder;
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mHolder = null;
		stopRecording();
	}
}
