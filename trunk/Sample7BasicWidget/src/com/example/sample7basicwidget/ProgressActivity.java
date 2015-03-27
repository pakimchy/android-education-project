package com.example.sample7basicwidget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ProgressActivity extends ActionBarActivity {

	ProgressBar progressView;
	EditText maxView, currentView, secondaryView;
	SeekBar seekView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
		seekView = (SeekBar)findViewById(R.id.seekBar1);
		progressView = (ProgressBar)findViewById(R.id.progressBar1);
		maxView = (EditText)findViewById(R.id.edit_max);
		secondaryView = (EditText)findViewById(R.id.edit_secondary);
		currentView = (EditText)findViewById(R.id.edit_current);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int max = Integer.parseInt(maxView.getText().toString());
				int secondary = Integer.parseInt(secondaryView.getText().toString());
				int current = Integer.parseInt(currentView.getText().toString());
				progressView.setMax(max);
				progressView.setSecondaryProgress(secondary);
				progressView.setProgress(current);
				seekView.setMax(max);
				seekView.setProgress(current);
			}
		});
		seekView.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int current = -1;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (current != -1) {
					currentView.setText(""+current);
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				current = -1;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					current = progress;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.progress, menu);
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
