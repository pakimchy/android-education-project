package com.example.sample7thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	ProgressBar progressView;
	TextView messageView;
	private static final int MESSAGE_PROGRESS = 1;
	private static final int MESSAGE_DONE = 2;
	
	Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_PROGRESS :
				int progress = msg.arg1;
				messageView.setText("progress : " + progress);
				progressView.setProgress(progress);
				break;
			case MESSAGE_DONE :
				messageView.setText("progress done ");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressView = (ProgressBar) findViewById(R.id.progressBar1);
		messageView = (TextView) findViewById(R.id.text_message);
		Button btn = (Button) findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				progressView.setMax(100);
				new Thread(new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i <= 100; i += 5) {
//							messageView.setText("progress : " + i);
//							progressView.setProgress(i);
							
//							Message msg = mHandler.obtainMessage(MESSAGE_PROGRESS, i, 0);
//							mHandler.sendMessage(msg);
							
							mHandler.post(new ProgressRunnable(i));
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
//						messageView.setText("progress done");
//						mHandler.sendEmptyMessage(MESSAGE_DONE);
						mHandler.post(new DoneRunnable());
					}
				}).start();
			}
		});
	}
	
	class ProgressRunnable implements Runnable {
		int progress;
		public ProgressRunnable(int progress) {
			this.progress = progress;
		}
		
		@Override
		public void run() {
			messageView.setText("progress : " + progress);
			progressView.setProgress(progress);
		}
	}
	
	class DoneRunnable implements Runnable{
		@Override
		public void run() {
			messageView.setText("progress done ");
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
