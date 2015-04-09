package com.example.sample7backpress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	public static final int MESSAGE_BACK_PRESSED_TIMEOUT = 0;
	public static final int TIMEOUT_BACK_PRESSED = 2000;
	
	Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_BACK_PRESSED_TIMEOUT :
				isBackPressed = false;
				break;
			}
		}
	};
	
	TextView messageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageView = (TextView)findViewById(R.id.text_message);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(updateRunnable);
				mHandler.post(updateRunnable);
			}
		});
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(updateRunnable);
			}
		});
    }

    boolean isBackPressed = false;
    @Override
    public void onBackPressed() {
    	if (!isBackPressed) {
    		Toast.makeText(this, "one more backkey...", Toast.LENGTH_SHORT).show();
    		mHandler.sendEmptyMessageDelayed(MESSAGE_BACK_PRESSED_TIMEOUT, TIMEOUT_BACK_PRESSED);
    		isBackPressed = true;
    	} else {
    		mHandler.removeMessages(MESSAGE_BACK_PRESSED_TIMEOUT);
    		super.onBackPressed();
    	}
    }
    
    int count = 0;
    Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			// ....
			messageView.setText("count : " + count);
			count++;
			mHandler.postDelayed(this, 1000);
		}
	};

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
