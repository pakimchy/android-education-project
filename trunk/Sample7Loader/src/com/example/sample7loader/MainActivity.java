package com.example.sample7loader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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


public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

	ListView listView;
	EditText keywordView;
	SimpleCursorAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        String[] from = {Contacts.DISPLAY_NAME};
        int[] to = {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
        listView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        keywordView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString();
				Bundle b = new Bundle();
				b.putString(FIELD_KEYWORD, keyword);
				getSupportLoaderManager().restartLoader(0, b, MainActivity.this);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
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


    private static final String FIELD_KEYWORD = "keyword";
    String[] projection = { Contacts._ID, Contacts.DISPLAY_NAME };
    String selection = "(("+Contacts.DISPLAY_NAME + " NOT NULL) AND (" + Contacts.DISPLAY_NAME + " != ''))";
    String sortOrder = Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
    
	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		String keyword = null;
		if (b != null) {
			keyword = b.getString(FIELD_KEYWORD);
		}
		Uri uri = Contacts.CONTENT_URI;
		if (keyword != null && !keyword.equals("")) {
			uri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(keyword));
		}
		return new CursorLoader(this, uri, projection, selection, null, sortOrder);
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		mAdapter.swapCursor(c);
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
