package com.example.examplecontactscontract;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Data;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailActivity extends ActionBarActivity {

	public static final String EXTRA_CONTACT_ID = "contact_id";
	public static final String EXTRA_LOOKUP_KEY = "lookup_key";

	ListView listView;
	SimpleCursorAdapter mAdapter;
	
	String[] projection = { Data._ID, Data.MIMETYPE, Data.DATA1, Data.DATA14};
	String selection = Data.CONTACT_ID + " = ?";
	String selectionLookupKey = Data.LOOKUP_KEY + " = ?";
	String[] selectionArgs = new String[1];
	String sortOrder = Data.MIMETYPE + " ASC";
	int mimetypeIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);
		listView = (ListView)findViewById(R.id.listView1);
		String[] from = {Data.MIMETYPE};
		int[] to = {android.R.id.text1};
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == mimetypeIndex) {
					String mimetype = cursor.getString(columnIndex);
					TextView tv = (TextView)view;
					if (mimetype.equals(CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {
						String name = cursor.getString(cursor.getColumnIndex(CommonDataKinds.StructuredName.DISPLAY_NAME));
						tv.setText("Name : " + name);
					} else if (mimetype.equals(CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
						String phone = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
						tv.setText("Phone : " + phone);
					} else if (mimetype.equals(CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
						String email = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Email.ADDRESS));
						tv.setText("Email : " + email);
					} else if (mimetype.equals(CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
						int photo_file_id = cursor.getInt(cursor.getColumnIndex(CommonDataKinds.Photo.PHOTO_FILE_ID));
						tv.setText("Photo : " + photo_file_id);
					} else {
						String data = cursor.getString(cursor.getColumnIndex(Data.DATA1));
						tv.setText(mimetype + " : "+data);
					}
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mAdapter);
		Intent intent = getIntent();
		long contactId = intent.getLongExtra(EXTRA_CONTACT_ID, -1);
		String lookupKey = intent.getStringExtra(EXTRA_LOOKUP_KEY);
		Cursor c = null;
		if (contactId != -1) {
			selectionArgs[0] = "" + contactId;
			c = getContentResolver().query(Data.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
		} else if (lookupKey != null && !lookupKey.equals("")){
			selectionArgs[0] = lookupKey;
			c = getContentResolver().query(Data.CONTENT_URI, projection, selectionLookupKey, selectionArgs, sortOrder);
		} else {
			Toast.makeText(this, "set contact id or lookup key", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		if (c != null) {
			mimetypeIndex = c.getColumnIndex(Data.MIMETYPE);
			mAdapter.changeCursor(c);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_detail, menu);
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
