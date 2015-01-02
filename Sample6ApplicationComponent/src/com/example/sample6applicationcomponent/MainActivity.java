package com.example.sample6applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	EditText nameView;
	
	public static final int REQUEST_CODE_MY = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nameView = (EditText)findViewById(R.id.edit_name);
		Button btn = (Button)findViewById(R.id.btn_my_activity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
//				i.putExtra(MyActivity.EXTRA_NAME, nameView.getText().toString());
//				i.putExtra(MyActivity.EXTRA_AGE, 41);
				Person p = new Person();
				p.name = nameView.getText().toString();
				p.age = 41;
				p.weight = 80;
				p.height = 180;
				
				i.putExtra(MyActivity.EXTRA_PERSON, p);
				startActivityForResult(i, REQUEST_CODE_MY);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_MY && resultCode == Activity.RESULT_OK) {
			String message = data.getStringExtra(MyActivity.RESULT_MESSAGE);
			Toast.makeText(this, "result : " + message, Toast.LENGTH_SHORT).show();
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
