package com.example.examplevideosurfaceplayer;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements SurfaceHolder.Callback{

	SurfaceView displayView;
	SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	enum PlayState {
		IDLE,
		STARTED,
		PAUSED
	}

	PlayState mState = PlayState.IDLE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayView = (SurfaceView)findViewById(R.id.surfaceView1);
		displayView.getHolder().addCallback(this);
		displayView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		Button btn = (Button)findViewById(R.id.btn_get_video);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, VideoListActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_play);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == PlayState.PAUSED) {
					if (mHolder != null) {
						mPlayer.start();
					}
					mState = PlayState.STARTED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_pause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == PlayState.STARTED) {
					mPlayer.pause();
					mState = PlayState.PAUSED;
				}
			}
		});
	}

	Uri mUri;
	int mCurrentPosition;
	
	private void setMediaPlayer() {
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
		}
		if (mUri != null) {
			try {
				mPlayer.setDataSource(this, mUri);
				mPlayer.prepare();
				if (mHolder != null) {
					mPlayer.setDisplay(mHolder);
					mPlayer.seekTo(mCurrentPosition);
					if (mState == PlayState.STARTED) {
						mPlayer.start();
					}
				}
			} catch (IllegalArgumentException | SecurityException
					| IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void releaseMediaPlayer() {
		if (mPlayer != null) {
			mCurrentPosition = mPlayer.getCurrentPosition();
			mPlayer.release();
			mPlayer = null;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			mUri = data.getData();
			mCurrentPosition = 0;
			mState = PlayState.STARTED;
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
		setMediaPlayer();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		releaseMediaPlayer();
		mHolder = null;
	}
}
