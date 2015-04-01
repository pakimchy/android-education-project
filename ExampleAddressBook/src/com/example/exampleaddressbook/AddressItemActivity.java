package com.example.exampleaddressbook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddressItemActivity extends ActionBarActivity {

	EditText nameView, phoneView, homeView, officeView;
	Address mAddress;
	public static final String EXTRA_ADDRESS = "address";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_item);
		nameView = (EditText)findViewById(R.id.edit_name);
		phoneView = (EditText)findViewById(R.id.edit_phone);
		homeView = (EditText)findViewById(R.id.edit_home);
		officeView = (EditText)findViewById(R.id.edit_office);
		
		mAddress = (Address)getIntent().getSerializableExtra(EXTRA_ADDRESS);
		
		if (mAddress != null) {
			nameView.setText(mAddress.name);
			phoneView.setText(mAddress.phone);
			homeView.setText(mAddress.home);
			officeView.setText(mAddress.office);
		}
		
		Button btn = (Button)findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Address addr = new Address();
				fillAddress(addr);
				AddressDBHelper.getInstance().insertAddressBook(addr);
				clearEditText();
			}
		});		
		btn.setVisibility((mAddress == null)?View.VISIBLE:View.GONE);
		
		btn = (Button)findViewById(R.id.btn_edit);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fillAddress(mAddress);
				AddressDBHelper.getInstance().updateAddressBook(mAddress);
			}
		});		
		btn.setVisibility((mAddress != null)?View.VISIBLE:View.GONE);
		
		btn = (Button)findViewById(R.id.btn_delete);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddressDBHelper.getInstance().deleteAddressBook(mAddress);
			}
		});		
		btn.setVisibility((mAddress != null)?View.VISIBLE:View.GONE);
		
		btn = (Button)findViewById(R.id.btn_finish);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void fillAddress(Address addr) {
		addr.name = nameView.getText().toString();
		addr.phone = phoneView.getText().toString();
		addr.home = homeView.getText().toString();
		addr.office = officeView.getText().toString();
	}
	
	private void clearEditText() {
		nameView.setText("");
		phoneView.setText("");
		homeView.setText("");
		officeView.setText("");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.address_item, menu);
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
