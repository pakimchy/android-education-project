package com.example.sample7mediaplayer;

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
	PlayState mState = PlayState.IDLE;
	enum PlayState {
		IDLE,
		INITIIALIZED,
		PREPARED,
		STARTED,
		PAUSED,
		STOPPED
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlayer = MediaPlayer.create(this, R.raw.winter_blues);
        mState = PlayState.PREPARED;
        
        Button btn = (Button)findViewById(R.id.btn_play);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				start();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_pause);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pause();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_stop);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stop();
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

    private void start() {
    	if (mState == PlayState.INITIIALIZED || mState == PlayState.STOPPED) {
    		try {
				mPlayer.prepare();
				mState = PlayState.PREPARED;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	if (mState == PlayState.PREPARED || mState == PlayState.PAUSED) {
    		mPlayer.start();
    		mState = PlayState.STARTED;
    	}
    }
    
    private void pause() {
    	if (mState == PlayState.STARTED) {
    		mPlayer.pause();
    		mState = PlayState.PAUSED;
    	}
    }
    
    private void stop() {
    	if (mState == PlayState.STARTED || mState == PlayState.PAUSED) {
    		mPlayer.stop();
    		mState = PlayState.STOPPED;
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
