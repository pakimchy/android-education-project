package com.example.sample5mediastore;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{

	ListView listView;
	SimpleCursorAdapter mAdapter;
	
	int idIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		String[] from = {MediaStore.Images.Media._ID, MediaStore.Images.Media.TITLE};
		int[] to = {R.id.thumb_image, R.id.title};
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == idIndex) {
					long id = cursor.getLong(columnIndex);
					Bitmap bm = Images.Thumbnails.getThumbnail(getContentResolver(), id, Images.Thumbnails.MICRO_KIND, null);
					ImageView iv = (ImageView)view;
					iv.setImageBitmap(bm);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(mAdapter);
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

	String[] projection = {Images.Media._ID, Images.Media.TITLE, Images.Media.DATE_ADDED};
	String selection = "((" + Images.Media.TITLE + " NOTNULL) AND (" + Images.Media.TITLE + " != ''))";
	String sortOrder = Images.Media.TITLE + " COLLATE LOCALIZED ASC";
	@Override
	public Loader<Cursor> onCreateLoader(int code, Bundle b) {
		return new CursorLoader(this, Images.Media.EXTERNAL_CONTENT_URI, projection, selection, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		idIndex = c.getColumnIndex(Images.Media._ID);
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}
}
