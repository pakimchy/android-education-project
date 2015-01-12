package com.example.sample6asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressText = (TextView)findViewById(R.id.text_progress);
		progressView = (ProgressBar)findViewById(R.id.progressBar1);
		Button btn = (Button)findViewById(R.id.btn_start);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MyTask().execute();
			}
		});
	}

	class MyTask extends AsyncTask<String, Integer, Boolean> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressText.setText("progress : 0");
			progressView.setMax(100);
			progressView.setProgress(0);
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			for (int i = 0 ; i < 20; i++) {
				int count = i * 5;
				publishProgress(count);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			int progress = values[0];
			progressText.setText("progress : " + progress);
			progressView.setProgress(progress);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
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
