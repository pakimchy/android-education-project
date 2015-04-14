package com.example.exampleimagemultiselect;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.Images;
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

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageChoiceActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {
	
	public static final String RESULT_LIST = "result_list";

	GridView gridView;
	SimpleCursorAdapter mAdapter;
	String[] projection = { Images.Media._ID, Images.Media.DISPLAY_NAME};
	String selection = Images.Media.MIME_TYPE + " LIKE ?";
	String[] selectionArgs = { "image/jpeg" };
	String sortOrder = Images.Media.DATE_ADDED + " DESC";
	int idColumn = -1;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_choice);
		gridView = (GridView)findViewById(R.id.gridView1);
		String[] from = {Images.Media.DISPLAY_NAME, Images.Media._ID};
		int[] to = {R.id.text_title, R.id.image_thumbnail};
		mAdapter = new SimpleCursorAdapter(this, R.layout.grid_item_checked, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == idColumn) {
					ImageView iv = (ImageView)view;
					long id = cursor.getLong(cursor.getColumnIndex(Images.Media._ID));
					Uri contentUri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id);
					ImageLoader.getInstance().displayImage(contentUri.toString(), iv);
					return true;
				}
				return false;
			}
		});
		gridView.setAdapter(mAdapter);
		gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
		Button btn = (Button)findViewById(R.id.btn_finish);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SparseBooleanArray checkedlist = gridView.getCheckedItemPositions();
				ArrayList<ImageItem> list = new ArrayList<ImageItem>();
				
				for (int i = 0; i < checkedlist.size(); i++) {
					int position = checkedlist.keyAt(i);
					if (checkedlist.get(position)) {
						ImageItem item = new ImageItem();
						Cursor c = (Cursor)gridView.getItemAtPosition(position);
						long id = c.getLong(c.getColumnIndex(Images.Media._ID));
						String title = c.getString(c.getColumnIndex(Images.Media.DISPLAY_NAME));
						item.uri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id);
						item.title = title;
						list.add(item);
					}
				}
				Intent data = new Intent();
				data.putParcelableArrayListExtra(RESULT_LIST, list);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_choice, menu);
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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		idColumn = cursor.getColumnIndex(Images.Media._ID);
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
