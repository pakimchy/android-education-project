package com.example.sample6threadhandler;

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

	TextView progressText;
	ProgressBar progressView;
	
	public static final int MESSAGE_TYPE_PROGRESS = 0;
	public static final int MESSAGE_TYPE_END = 1;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_TYPE_PROGRESS :
				int progress = msg.arg1;
				progressText.setText("progress : " + progress);
				progressView.setProgress(progress);
				break;
			case MESSAGE_TYPE_END :
				progressText.setText("progress end");
				progressView.setProgress(100);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressText =(TextView)findViewById(R.id.text_progress);
		progressView = (ProgressBar)findViewById(R.id.progressBar1);
		progressView.setMax(100);
		
		
		Button btn = (Button)findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progressText.setText("progress : 0");
				progressView.setProgress(0);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						for (int i = 0; i < 20; i++) {
							int count = i * 5;
							
							mHandler.post(new ProgressRunnable(count));
//							Message msg = mHandler.obtainMessage(MESSAGE_TYPE_PROGRESS, count, 0);
//							mHandler.sendMessage(msg);
							
//							progressText.setText("progress : " + count);
//							progressView.setProgress(count);
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						mHandler.post(new EndRunnable());
						
//						mHandler.sendEmptyMessage(MESSAGE_TYPE_END);
						
//						progressText.setText("progress end");
//						progressView.setProgress(100);
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
			progressText.setText("progress : " + progress);
			progressView.setProgress(progress);
		}
	}
	
	class EndRunnable implements Runnable {
		@Override
		public void run() {
			progressText.setText("progress end");
			progressView.setProgress(100);
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
