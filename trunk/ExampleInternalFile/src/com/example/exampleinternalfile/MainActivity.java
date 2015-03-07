package com.example.exampleinternalfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private static final String FILE_NAME = "note.txt";
	EditText contentView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contentView = (EditText)findViewById(R.id.edit_content);
		Button btn = (Button)findViewById(R.id.btn_read);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					FileInputStream fis = openFileInput(FILE_NAME);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					StringBuilder sb = new StringBuilder();
					String line;
					while((line = br.readLine()) != null) {
						sb.append(line);
						sb.append("\r\n");
					}
					contentView.setText(sb.toString());
					Toast.makeText(MainActivity.this, "success file read", Toast.LENGTH_SHORT).show();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		btn = (Button)findViewById(R.id.btn_write);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					bw.write(contentView.getText().toString());
					bw.flush();
					bw.close();
					Toast.makeText(MainActivity.this, "success file write", Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
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
