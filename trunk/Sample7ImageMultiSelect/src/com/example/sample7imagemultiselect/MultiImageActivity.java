package com.example.sample7imagemultiselect;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

public class MultiImageActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{

	GridView gridView;
	SimpleCursorAdapter mAdapter;
	int dataIndex = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_image);
		gridView = (GridView)findViewById(R.id.gridView1);
		String[] from = {Images.Media.DISPLAY_NAME, Images.Media.DATA};
		int[] to = {R.id.text_name, R.id.image_content};
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_image, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == dataIndex) {
					ImageView iv = (ImageView)view;
					String path = cursor.getString(columnIndex);
					Uri uri = Uri.fromFile(new File(path));
					ImageLoader.getInstance().displayImage(uri.toString(), iv);
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
				SparseBooleanArray array = gridView.getCheckedItemPositions();
				ArrayList<ImageItem> list = new ArrayList<ImageItem>();
				for (int i = 0; i < array.size(); i++) {
					int position = array.keyAt(i);
					if (array.get(position)) {
						Cursor c = (Cursor)gridView.getItemAtPosition(position);
						long id = c.getLong(c.getColumnIndex(Images.Media._ID));
						Uri uri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id);
						String name = c.getString(c.getColumnIndex(Images.Media.DISPLAY_NAME));
						String path = c.getString(c.getColumnIndex(Images.Media.DATA));
						ImageItem ii = new ImageItem(uri, name, path);
						list.add(ii);
					}
				}
				Intent data = new Intent();
				data.putParcelableArrayListExtra("images", list);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.multi_image, menu);
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
		String[] projection = {Images.Media._ID, Images.Media.DISPLAY_NAME, Images.Media.DATA};
		String sortOrder = Images.Media.DATE_ADDED + " DESC";
		return new CursorLoader(this, Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		dataIndex = data.getColumnIndex(Images.Media.DATA);
		mAdapter.swapCursor(data);
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
