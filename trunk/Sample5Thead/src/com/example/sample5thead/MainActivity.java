package com.example.sample5thead;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	ProgressBar progressBar;
	TextView messageView;
	public static final int MESSAGE_PROGRESS = 0;
	public static final int MESSAGE_DONE = 1;
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_PROGRESS :
				int progress = msg.arg1;
				progressBar.setProgress(progress);
				messageView.setText("progress : " + progress);
				break;
			case MESSAGE_DONE :
				progressBar.setProgress(100);
				messageView.setText("progress done");
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		messageView = (TextView)findViewById(R.id.message);
		
		progressBar.setMax(100);
		
		Button btn = (Button)findViewById(R.id.btnStart);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						int count = 0;
						while(count < 20) {
//							progressBar.setProgress(count * 5);
//							messageView.setText("progress : " + count * 5);
							
//							Message msg = mHandler.obtainMessage(MESSAGE_PROGRESS, count * 5, 0);
//							mHandler.sendMessage(msg);
							
							mHandler.post(new ProgressRunnable(count * 5));
							
							count++;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
//						progressBar.setProgress(100);
//						messageView.setText("progress done");
						
//						Message msg = mHandler.obtainMessage(MESSAGE_DONE);
//						mHandler.sendMessage(msg);
						
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
			progressBar.setProgress(progress);
			messageView.setText("progress : " + progress);
		}
	}
	
	class DoneRunnable implements Runnable {
		@Override
		public void run() {
			progressBar.setProgress(100);
			messageView.setText("progress done");
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
