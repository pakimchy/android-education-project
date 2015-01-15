package com.example.sample6cursorloader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{

	String[] projection = { Contacts._ID,
			ContactsContract.Contacts.DISPLAY_NAME };
	String selection = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND " + "("
			+ Contacts.DISPLAY_NAME + " != ''))";
	String sortOrder = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	ListView listView;
	SimpleCursorAdapter mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{Contacts.DISPLAY_NAME}, new int[]{android.R.id.text1}, 0);
		listView.setAdapter(mAdapter);
		
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				if (keyword != null && !keyword.equals("")) {
					Bundle b = new Bundle();
					b.putString("keyword", keyword);
					getSupportLoaderManager().restartLoader(0, b, MainActivity.this);
				} else {
					getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
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

	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle bundle) {
		Uri uri = Contacts.CONTENT_URI;
		if (bundle != null) {
			String keyword = bundle.getString("keyword");
			if (keyword != null && !keyword.equals("")) {
				uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
			}
		}
		return new CursorLoader(this, uri, projection, selection, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		mAdapter.changeCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.changeCursor(null);
	}
}
