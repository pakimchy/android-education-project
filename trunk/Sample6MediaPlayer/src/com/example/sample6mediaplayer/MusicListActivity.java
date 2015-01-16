package com.example.sample6mediaplayer;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MusicListActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	ListView listView;
	SimpleCursorAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_list);
		listView = (ListView)findViewById(R.id.listView1);
		String[] from = {MediaStore.Audio.Media.TITLE};
		int[] to = {android.R.id.text1};
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Uri uri = ContentUris.withAppendedId(Audio.Media.EXTERNAL_CONTENT_URI, id);
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				String title = c.getString(c.getColumnIndex(Audio.Media.TITLE));
				Intent data = new Intent();
				data.setData(uri);
				data.putExtra("title", title);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_list, menu);
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
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = { Audio.Media._ID, Audio.Media.TITLE };
		
		return new CursorLoader(this, Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		mAdapter.changeCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.changeCursor(null);
	}
}
