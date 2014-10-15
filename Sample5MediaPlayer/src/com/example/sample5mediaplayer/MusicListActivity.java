package com.example.sample5mediaplayer;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MusicListActivity extends ActionBarActivity 
implements LoaderCallbacks<Cursor>{

	/** Called when the activity is first created. */
	ListView listView;
	SimpleCursorAdapter mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.list_layout);
	    listView = (ListView)findViewById(R.id.listView1);
	    String[] from = {MediaStore.Audio.Media.TITLE};
	    int[] to = {android.R.id.text1};
	    
	    mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
	    listView.setAdapter(mAdapter);
	    
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = (Cursor)listView.getItemAtPosition(position);
				String title = c.getString(c.getColumnIndex(Audio.Media.TITLE));
				Intent  i = new Intent();
				Uri uri = ContentUris.withAppendedId(Audio.Media.EXTERNAL_CONTENT_URI, id);
				i.setData(uri);
				i.putExtra("title", title);
				setResult(RESULT_OK, i);
				finish();
			}
		});
	    
	    getSupportLoaderManager().initLoader(0, null, this);
	}
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = {Audio.Media._ID, Audio.Media.TITLE};
		return new CursorLoader(this, Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	}
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		mAdapter.swapCursor(arg1);
	}
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}

}
