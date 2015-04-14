package com.example.examplecontactscontract;

import java.util.ArrayList;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ContactAddActivity extends ActionBarActivity {

	Spinner spinner;
	ArrayAdapter<String> mAdapter;
	EditText nameView, phoneView;
	AccountManager mAM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_add);
		spinner = (Spinner)findViewById(R.id.spinner1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(mAdapter);
		mAM = (AccountManager)getSystemService(Context.ACCOUNT_SERVICE);
		Account[] accounts = mAM.getAccounts();
		for (Account acc : accounts) {
			mAdapter.add(acc.name+" "+acc.type);
		}
		nameView = (EditText)findViewById(R.id.edit_name);
		phoneView = (EditText)findViewById(R.id.edit_phone);
		Button btn = (Button)findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String account = (String)spinner.getItemAtPosition(spinner.getSelectedItemPosition());
				ContentValues values = new ContentValues();
				String[] acc = account.split(" ");
				values.put(RawContacts.ACCOUNT_NAME, acc[0]);
				values.put(RawContacts.ACCOUNT_TYPE, acc[1]);
				Uri rawUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
				long rawId = ContentUris.parseId(rawUri);
				
				values.clear();
				values.put(Data.RAW_CONTACT_ID, rawId);
				values.put(Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
				values.put(CommonDataKinds.StructuredName.DISPLAY_NAME, nameView.getText().toString());
				getContentResolver().insert(Data.CONTENT_URI, values);
				
				values.clear();
				values.put(Data.RAW_CONTACT_ID, rawId);
				values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
				values.put(CommonDataKinds.Phone.NUMBER, phoneView.getText().toString());
				values.put(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_HOME);
				
				getContentResolver().insert(Data.CONTENT_URI, values);
				
			}
		});
	}

	private void addContactsUsingContentProivderOperations() {
		String account = (String)spinner.getItemAtPosition(spinner.getSelectedItemPosition());
		ContentValues values = new ContentValues();
		String[] acc = account.split(" ");
		
		try {
			ArrayList<ContentProviderOperation> opts = new ArrayList<ContentProviderOperation>();
			opts.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
					.withValue(RawContacts.ACCOUNT_NAME, acc[0])
					.withValue(RawContacts.ACCOUNT_TYPE, acc[1])
					.build());
			opts.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
					.withValueBackReference(Data.RAW_CONTACT_ID, 0)
					.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
					.withValue(StructuredName.DISPLAY_NAME, nameView.getText().toString())
					.build());
			opts.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
					.withValueBackReference(Data.RAW_CONTACT_ID, 0)
					.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
					.withValue(Phone.DISPLAY_NAME, phoneView.getText().toString())
					.build());
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, opts);
		} catch (RemoteException | OperationApplicationException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_add, menu);
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
