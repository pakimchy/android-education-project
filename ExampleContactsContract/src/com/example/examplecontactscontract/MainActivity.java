package com.example.examplecontactscontract;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.DisplayPhoto;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.QuickContactBadge;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	EditText keywordView;
	SimpleCursorAdapter mAdapter;
	
	String[] projection = {Contacts._ID, Contacts.DISPLAY_NAME, Contacts.LOOKUP_KEY, Contacts.PHOTO_FILE_ID};
	String selection = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND (" +
					Contacts.DISPLAY_NAME + " != ''))";
	String sortOrder = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	int idIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		String[] from = { Contacts._ID, Contacts.DISPLAY_NAME };
		int[] to = { R.id.quick_photo, R.id.text_name };
		mAdapter = new SimpleCursorAdapter(this,
				R.layout.contacts_item, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == idIndex) {
					QuickContactBadge badge = (QuickContactBadge)view;
					long id = cursor.getLong(columnIndex);
					Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
					long photoid = cursor.getLong(cursor.getColumnIndex(Contacts.PHOTO_FILE_ID));
					Bitmap bm = null;
					if (photoid > 0) {
						Uri photouri = ContentUris.withAppendedId(DisplayPhoto.CONTENT_URI, photoid);
						try {
							AssetFileDescriptor fd = getContentResolver().openAssetFileDescriptor(photouri, "r");
							bm = BitmapFactory.decodeStream(fd.createInputStream());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
					if (bm != null) {
						badge.setImageBitmap(bm);
					} else {
						badge.setImageToDefault();
					}
					badge.assignContactUri(uri);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				String lookupKey = c.getString(c.getColumnIndex(Contacts.LOOKUP_KEY));
				Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
//				intent.putExtra(ContactDetailActivity.EXTRA_CONTACT_ID, id);
				intent.putExtra(ContactDetailActivity.EXTRA_LOOKUP_KEY, lookupKey);
				startActivity(intent);
			}
		});
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				searchContacts(keyword);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		searchContacts(null);
	}
	
	
	private void searchContactsUsingPhone(String number) {
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
		String[] projection = { PhoneLookup._ID, PhoneLookup.DISPLAY_NAME, PhoneLookup.NUMBER };
		String sortOrder = PhoneLookup.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		Cursor c = getContentResolver().query(uri, projection, null, null, sortOrder);
	}
	
	private void searchContactsUsingLookup(String lookupKey) {
		Uri uri = Uri.withAppendedPath(Contacts.CONTENT_LOOKUP_URI, Uri.encode(lookupKey));
		Cursor c = getContentResolver().query(uri, projection, selection, null, sortOrder);
	}
	
	private void searchContacts(String keyword) {
		Uri uri = Contacts.CONTENT_URI;
		if (keyword != null && !keyword.equals("")) {
			uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		}
		Cursor c = getContentResolver().query(uri, projection, selection, null, sortOrder);
		idIndex = c.getColumnIndex(Contacts._ID);
		mAdapter.changeCursor(c);
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
			startActivity(new Intent(this, ContactAddActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
