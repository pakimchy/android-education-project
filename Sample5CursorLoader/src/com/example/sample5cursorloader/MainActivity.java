package com.example.sample5cursorloader;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.QuickContactBadge;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{

	ListView listView;
	EditText keywordView;
	SimpleCursorAdapter mAdapter;
	
	String[] projection = {Contacts._ID, Contacts.DISPLAY_NAME};
	String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
            + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
	String orderBy = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
	
	int idColumn = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.keyword);
		Button btn = (Button)findViewById(R.id.btn_search);
		String[] from = {Contacts._ID, Contacts.DISPLAY_NAME};
		int[] to = {R.id.quickContactBadge1, R.id.textView1};
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor c, int columnIndex) {
				if (columnIndex == idColumn) {
					QuickContactBadge badge = (QuickContactBadge)view;
					long id = c.getLong(columnIndex);
					Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
					badge.assignContactUri(uri);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mAdapter);
		
		keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				Bundle b = null;
				if (keyword != null && !keyword.equals("")) {
					b = new Bundle();
					b.putString("keyword", keyword);
				}
				getSupportLoaderManager().restartLoader(0, b, MainActivity.this);
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
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		Uri uri = Contacts.CONTENT_URI;
		if (b != null) {
			String keyword = b.getString("keyword");
			if (keyword != null && !keyword.equals("")) {
				uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
			}
		}
		return new CursorLoader(this, uri, projection, selection, null, orderBy);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		idColumn = cursor.getColumnIndex(Contacts._ID);
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
