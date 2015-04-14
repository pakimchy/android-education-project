package com.example.exampleaudioplayer;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Audio;
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
	private static final String TAG = "MainActivity";

	enum PlayState {
		IDLE, INITIALIED, PREPARED, STARTED, PAUSED, // PLAYBACK_COMPLETED
		STOPPED, ERROR, END
	}

	PlayState mState = PlayState.IDLE;
	SeekBar progressView;
	Handler mHandler = new Handler(Looper.getMainLooper());
	public static final int UPDATE_INTERVAL = 100;
	boolean isSeeking = false;
	private int seekPosition = -1;
	AudioManager mAudioManager;
	SeekBar volumeView;
	
	CheckBox muteView;
	float currentVolume = 1.0f;
	private static final float VOLUME_GAP = 0.1f;
	public static final int MUTE_INTERVAL = 100;
	Runnable smoothVolumeDown = new Runnable() {
		
		@Override
		public void run() {
			if (mPlayer != null) {
				if (currentVolume > 0) {
					mPlayer.setVolume(currentVolume, currentVolume);
					currentVolume -= VOLUME_GAP;
					mHandler.postDelayed(this, MUTE_INTERVAL);
				} else {
					currentVolume = 0;
					mPlayer.setVolume(0, 0);
				}
			}
		}
	};

	Runnable smoothVolumeUp = new Runnable() {
		
		@Override
		public void run() {
			if (mPlayer != null) {
				if (currentVolume < 1) {
					mPlayer.setVolume(currentVolume, currentVolume);
					currentVolume += VOLUME_GAP;
					mHandler.postDelayed(this, MUTE_INTERVAL);
				} else {
					currentVolume = 1.0f;
					mPlayer.setVolume(1.0f, 1.0f);
				}
			}
		}
	};
	
	Runnable progressUpdate = new Runnable() {

		@Override
		public void run() {
			if (mState == PlayState.STARTED) {
				if (!isSeeking) {
					progressView.setProgress(mPlayer.getCurrentPosition());
				}
				mHandler.postDelayed(this, UPDATE_INTERVAL);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressView = (SeekBar) findViewById(R.id.seek_progress);
		mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
		mState = PlayState.PREPARED;
		progressView.setMax(mPlayer.getDuration());

		progressView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int position = -1;

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (position != -1) {
					if (mState == PlayState.STARTED) {
						mPlayer.seekTo(position);
					} else {
						seekPosition = position;
					}
				}
				isSeeking = false;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				isSeeking = true;
				position = -1;
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					this.position = progress;
				}
			}
		});
		
		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		volumeView = (SeekBar)findViewById(R.id.seek_volume);
		volumeView.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		volumeView.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
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
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				}
			}
		});

		muteView = (CheckBox)findViewById(R.id.check_mute);
		muteView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mute(isChecked);
			}
		});
		mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mState = PlayState.ERROR;
				return false;
			}
		});

		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mState = PlayState.PAUSED;
			}
		});

		Button btn = (Button) findViewById(R.id.btn_play);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				playAudio();
			}
		});

		btn = (Button) findViewById(R.id.btn_pause);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pauseAudio();
			}
		});

		btn = (Button) findViewById(R.id.btn_stop);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stopAudio();
			}
		});
		registerReceiver(mNoisyReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
		registerReceiver(mHeadsetPlugged, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
	}
	
	boolean isPlugged = false;
	BroadcastReceiver mHeadsetPlugged = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int plugged = intent.getIntExtra("state", 0);
			isPlugged = (plugged == 0) ? false : true;
		}
	};
	
	BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			pauseAudio();
			isTemporaryPaused = false;
			isTemporaryMute = false;
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPlayer.release();
		mPlayer = null;
		mState = PlayState.END;
		unregisterReceiver(mNoisyReceiver);
	}
	

	boolean isTemporaryPaused = false;

	@Override
	protected void onStop() {
		super.onStop();
		if (mState == PlayState.STARTED) {
			mPlayer.pause();
			mState = PlayState.PAUSED;
			isTemporaryPaused = true;
		} else {
			isTemporaryPaused = false;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isTemporaryPaused && mState == PlayState.PAUSED) {
			mPlayer.start();
			mState = PlayState.STARTED;
			mHandler.post(progressUpdate);
		}
	}
	
	private void mute(boolean isMute) {
		mHandler.removeCallbacks(smoothVolumeDown);
		mHandler.removeCallbacks(smoothVolumeUp);
		if (isMute) {
			mHandler.post(smoothVolumeDown);
		} else {
			mHandler.post(smoothVolumeUp);
		}		
	}
	
	private void stopAudio() {
		if (mState == PlayState.STARTED || mState == PlayState.PAUSED) {
			mPlayer.stop();
			mState = PlayState.STOPPED;
			seekPosition = 0;
			progressView.setProgress(seekPosition);
		}
	}
	
	private void pauseAudio() {
		if (mState == PlayState.STARTED) {
			mPlayer.pause();
			mState = PlayState.PAUSED;
			seekPosition = mPlayer.getCurrentPosition();
		}
	}
	
	boolean isTemporaryMute = false;
	
	private void playAudio() {
		if (mState == PlayState.INITIALIED
				|| mState == PlayState.STOPPED) {
			try {
				mPlayer.prepare();
				mState = PlayState.PREPARED;
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}

		if (mState == PlayState.PREPARED || mState == PlayState.PAUSED) {
			int result = mAudioManager.requestAudioFocus(new OnAudioFocusChangeListener() {
				
				@Override
				public void onAudioFocusChange(int focusChange) {
					switch(focusChange) {
					case AudioManager.AUDIOFOCUS_LOSS :
						if (mState == PlayState.STARTED) {
							stopAudio();
							isTemporaryPaused = false;
							isTemporaryMute = false;
						}
						break;
					case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
						if (mState == PlayState.STARTED) {
							pauseAudio();
							isTemporaryPaused = true;
							isTemporaryMute = false;
						}
						break;
					case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
						if (mState == PlayState.STARTED) {
							mute(true);
							isTemporaryPaused = false;
							isTemporaryMute = true;
						}
						break;
					case AudioManager.AUDIOFOCUS_GAIN :
						if (isTemporaryPaused) {
							playAudio();
						} else if (isTemporaryMute) {
							mute(false);
						}
						break;
					}
				}
			}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
			if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				if (seekPosition != -1) {
					mPlayer.seekTo(seekPosition);
				}			
				mPlayer.start();
				mState = PlayState.STARTED;
				mHandler.post(progressUpdate);
			}
		}
	}


	public void setMediaSourceInResource() {
		try {
			AssetFileDescriptor afd = getResources().openRawResourceFd(
					R.raw.winter_blues);
			mPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
		} catch (IllegalArgumentException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	public void setMediaSourceInAsset() {
		try {
			AssetFileDescriptor afd = getAssets().openFd("winter_blues.mp3");
			mPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMediaSourceInFile(String path) {

		try {
			mPlayer.setDataSource(path);
		} catch (IllegalArgumentException | SecurityException
				| IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	public void setMediaSourceInMediaStore(int id) {
		try {
			Uri uri = ContentUris.withAppendedId(
					Audio.Media.EXTERNAL_CONTENT_URI, id);
			mPlayer.setDataSource(this, uri);
		} catch (IllegalArgumentException | SecurityException
				| IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	public void setMediaSourceInInternet(String url) {
		try {
			Uri uri = Uri.parse(url);
			mPlayer.setDataSource(this, uri);
		} catch (IllegalArgumentException | SecurityException
				| IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	Equalizer mEq;
	private void setEqualizer(short band, short level) {
		if (mEq == null) {
			mEq = new Equalizer(0, mPlayer.getAudioSessionId());
		}
		short numberOfBands = mEq.getNumberOfBands();
		for (short i = 0 ; i < numberOfBands; i++) {
			int[] freq = mEq.getBandFreqRange(i);
			Log.i(TAG, (freq[0] / 1000) + "Hz ~ " + (freq[1] / 1000) + "Hz : " + mEq.getBandLevel(i) );
		}
		if (band  < numberOfBands) {
			short[] range = mEq.getBandLevelRange();
			if (level >= range[0] && level <= range[1]) {
				mEq.setBandLevel(band, level);
			}
		}
	}
	
	private void setEqualizer(short preset) {
		if (mEq == null) {
			mEq = new Equalizer(0, mPlayer.getAudioSessionId());
		}
		short numberOfPreset = mEq.getNumberOfPresets();
		for (short i = 0; i < numberOfPreset; i++) {
			String name = mEq.getPresetName(i);
			Log.i(TAG, "preset name : " + name);
		}
		if (preset < numberOfPreset) {
			mEq.usePreset(preset);
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
