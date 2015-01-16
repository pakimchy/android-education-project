package com.example.sample6mediaplayer;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	MediaPlayer mPlayer;
	private static final int STATE_IDLE = 0;
	private static final int STATE_INITIALED = 1;
	private static final int STATE_PREPARED = 2;
	private static final int STATE_STARTED = 3;
	private static final int STATE_PAUSED = 4;
	private static final int STATE_STOPPED = 5;
	private static final int STATE_ERROR = 6;
	
	int mState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = STATE_PREPARED;
		
		Button btn = (Button)findViewById(R.id.btn_play);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == STATE_INITIALED || mState == STATE_STOPPED) {
					try {
						mPlayer.prepare();
						mState = STATE_PREPARED;
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (mState == STATE_PREPARED || mState == STATE_PAUSED) {
					mPlayer.start();
					mState = STATE_STARTED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_pause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == STATE_STARTED) {
					mPlayer.pause();
					mState = STATE_PAUSED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == STATE_STARTED || mState == STATE_PAUSED) {
					mPlayer.stop();
					mState = STATE_STOPPED;
				}
			}
		});
		
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mState = STATE_PAUSED;
			}
		});
		
		mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mState = STATE_IDLE;
				mPlayer.reset();
				return false;
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
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
