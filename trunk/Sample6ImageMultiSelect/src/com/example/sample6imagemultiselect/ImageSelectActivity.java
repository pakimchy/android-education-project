package com.example.sample6imagemultiselect;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageSelectActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	GridView gridView;
	SimpleCursorAdapter mAdapter;

	int idIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_select);
		gridView = (GridView) findViewById(R.id.gridView1);
		String[] from = { Images.Media._ID };
		int[] to = { R.id.image_content };
		mAdapter = new SimpleCursorAdapter(this, R.layout.grid_item_layout,
				null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == idIndex) {
					long id = cursor.getLong(columnIndex);
					Bitmap bm = Images.Thumbnails.getThumbnail(getContentResolver(), id, Thumbnails.MINI_KIND, null);
					((ImageView)view).setImageBitmap(bm);
					return true;
				}
				return false;
			}
		});
		gridView.setAdapter(mAdapter);
		gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
		getSupportLoaderManager().initLoader(0, null, this);
		
		Button btn = (Button)findViewById(R.id.btn_select);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SparseBooleanArray array = gridView.getCheckedItemPositions();
				ArrayList<Uri> list = new ArrayList<Uri>();
				for (int index = 0; index < array.size(); index++) {
					int position = array.keyAt(index);
					if (array.get(position)) {
						long id = gridView.getItemIdAtPosition(position);
						list.add(ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id));
					}
				}
				
				Intent data = new Intent();
				Uri[] uris = list.toArray(new Uri[list.size()]);
				data.putExtra("result", uris);
				setResult(Activity.RESULT_OK,data);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_select, menu);
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
		return new CursorLoader(this, Images.Media.EXTERNAL_CONTENT_URI, new String[]{Images.Media._ID}, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		idIndex = arg1.getColumnIndex(Images.Media._ID);
		mAdapter.changeCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.changeCursor(null);
	}
}
