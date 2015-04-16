package com.example.sample7database;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContentActivity extends ActionBarActivity {

	EditText nameView, emailView, phoneView, addrView;
	ItemData itemData;
	
	public static final String EXTRA_ITEM = "item";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		nameView = (EditText)findViewById(R.id.edit_name);
		emailView = (EditText)findViewById(R.id.edit_email);
		phoneView = (EditText)findViewById(R.id.edit_phone);
		addrView = (EditText)findViewById(R.id.edit_address);
		Button btn = (Button)findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (itemData == null) {
					itemData = new ItemData();
				}
				itemData.name = nameView.getText().toString();
				itemData.email = emailView.getText().toString();
				itemData.phone = phoneView.getText().toString();
				itemData.address = addrView.getText().toString();
				DBManager.getInstance().addItem(itemData);
			}
		});
		itemData = (ItemData)getIntent().getSerializableExtra(EXTRA_ITEM);
		if (itemData != null) {
			nameView.setText(itemData.name);
			emailView.setText(itemData.email);
			phoneView.setText(itemData.phone);
			addrView.setText(itemData.address);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content, menu);
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
