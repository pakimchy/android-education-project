package com.example.sample6mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends ActionBarActivity {

	MediaPlayer mPlayer;
	private static final int STATE_IDLE = 0;
	private static final int STATE_INITIALED = 1;
	private static final int STATE_PREPARED = 2;
	private static final int STATE_STARTED = 3;
	private static final int STATE_PAUSED = 4;
	private static final int STATE_STOPPED = 5;
	private static final int STATE_ERROR = 6;

	SeekBar progressView;
	SeekBar volumeView;
	AudioManager mAudioManager;
	CheckBox muteView;

	int mState;

	Handler mHandler = new Handler();
	private static final int INTERVAL_PROGRESS = 100;
	boolean bSeekStart = false;
	Runnable updateRunnable = new Runnable() {

		@Override
		public void run() {
			if (mState == STATE_STARTED && mPlayer != null) {
				if (!bSeekStart) {
					progressView.setProgress(mPlayer.getCurrentPosition());
				}
				mHandler.postDelayed(this, INTERVAL_PROGRESS);
			}
		}
	};

	float volume = 1.0f;
	Runnable muteRunnable = new Runnable() {

		@Override
		public void run() {
			if (volume < 0) {
				volume = 0;
			}
			mPlayer.setVolume(volume, volume);
			Log.i("MainActivity", "volume : " + volume);
			if (volume > 0) {
				volume -= 0.1f;
				mHandler.postDelayed(this, INTERVAL_PROGRESS);
			}
		}
	};
	
	Runnable immuteRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (volume > 1) {
				volume = 1.0f;
			}
			Log.i("MainActivity","volume : " + volume);
			mPlayer.setVolume(volume, volume);
			if (volume < 1) {
				volume+=0.1;
				mHandler.postDelayed(this, INTERVAL_PROGRESS);
			}
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressView = (SeekBar) findViewById(R.id.seek_progress);

		progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			private static final int NOT_CHAGNGED = -1;
			int progress;

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (progress != NOT_CHAGNGED) {
					if (mState == STATE_STARTED) {
						mPlayer.seekTo(progress);
					}
				}
				bSeekStart = false;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				progress = NOT_CHAGNGED;
				bSeekStart = true;
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					this.progress = progress;
				}

			}
		});

		volumeView = (SeekBar) findViewById(R.id.seek_volume);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volumeView.setMax(maxVolume);
		int currentVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		volumeView.setProgress(currentVolume);
		volumeView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
							progress, 0);
				}
			}
		});

		muteView = (CheckBox) findViewById(R.id.check_mute);
		muteView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					mHandler.removeCallbacks(immuteRunnable);
					mHandler.post(muteRunnable);
				} else {
					mHandler.removeCallbacks(muteRunnable);
					mHandler.post(immuteRunnable);
				}
			}
		});

		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = STATE_PREPARED;
		int duration = mPlayer.getDuration();
		progressView.setMax(duration);
		Button btn = (Button) findViewById(R.id.btn_play);
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
					mPlayer.seekTo(progressView.getProgress());
					mPlayer.start();
					mState = STATE_STARTED;
					mHandler.post(updateRunnable);
				}
			}
		});

		btn = (Button) findViewById(R.id.btn_pause);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mState == STATE_STARTED) {
					mPlayer.pause();
					mState = STATE_PAUSED;
				}
			}
		});

		btn = (Button) findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mState == STATE_STARTED || mState == STATE_PAUSED) {
					mPlayer.stop();
					mState = STATE_STOPPED;
					progressView.setProgress(0);
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_music);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MusicListActivity.class);
				startActivityForResult(intent, 0);
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
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 0 && arg1 == Activity.RESULT_OK) {
			Uri uri = arg2.getData();
			String title = arg2.getStringExtra("title");
			mPlayer.reset();
			mState = STATE_IDLE;
			try {
				mPlayer.setDataSource(this, uri);
				mState = STATE_INITIALED;
				mPlayer.prepare();
				mState = STATE_PREPARED;
				progressView.setMax(mPlayer.getDuration());
				progressView.setProgress(0);
			} catch (IllegalArgumentException | SecurityException
					| IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (mState == STATE_STARTED) {
			mPlayer.pause();
			mState = STATE_PAUSED;
		}
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
