package com.example.sample5mediaplayer;

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
	public static final int PLAYER_STATE_IDLE = 0;
	public static final int PLAYER_STATE_INITIALIZED = 1;
	public static final int PLAYER_STATE_PREPARED = 2;
	public static final int PLAYER_STATE_STARTED = 3;
	public static final int PLAYER_STATE_PAUSED = 4;
	public static final int PLAYER_STATE_STOPPED = 5;
	
	int mState;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = PLAYER_STATE_PREPARED;
		Button btn = (Button)findViewById(R.id.btn_play);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == PLAYER_STATE_INITIALIZED ||
						mState == PLAYER_STATE_STOPPED) {
					try {
						mPlayer.prepare();
						mState = PLAYER_STATE_PREPARED;
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				}
				
				if (mState == PLAYER_STATE_PREPARED ||
						mState == PLAYER_STATE_PAUSED) {
					mPlayer.start();
					mState = PLAYER_STATE_STARTED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_pause);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == PLAYER_STATE_STARTED) {
					mPlayer.pause();
					mState = PLAYER_STATE_PAUSED;
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mState == PLAYER_STATE_STARTED || mState == PLAYER_STATE_PAUSED) {
					mPlayer.stop();
					mState = PLAYER_STATE_STOPPED;
				}
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
