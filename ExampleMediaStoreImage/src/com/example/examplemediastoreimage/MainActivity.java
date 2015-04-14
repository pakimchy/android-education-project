package com.example.examplemediastoreimage;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

	GridView gridView;
	SimpleCursorAdapter mAdapter;
	String[] projection = { Images.Media._ID, Images.Media.DISPLAY_NAME, Images.Media.DATA };
	String selection = Images.Media.MIME_TYPE + " LIKE ?";
	String[] selectionArgs = { "image/jpeg" };
	String sortOrder = Images.Media.DATE_ADDED + " DESC";

	int dataColumn = -1;
	BitmapFactory.Options opts = new BitmapFactory.Options();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (GridView)findViewById(R.id.gridView1);
		String[] from = {Images.Media.DISPLAY_NAME, Images.Media.DATA};
		int[] to = {R.id.text_title, R.id.image_thumbnail};
		mAdapter = new SimpleCursorAdapter(this, R.layout.item_image, null, from, to, 0);
		mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == dataColumn) {
					ImageView iv = (ImageView)view;
					// file path
//					String path = cursor.getString(columnIndex);
//					opts.inSampleSize = 4;
//					Bitmap bm = BitmapFactory.decodeFile(path, opts);
//					iv.setImageBitmap(bm);

					// ParcelFileDescripto
//					try {
//						long id = cursor.getLong(cursor.getColumnIndex(Images.Media._ID));
//						Uri contentUri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id);
//						ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(contentUri, "r");
//						opts.inSampleSize = 4;
//						Bitmap bm = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, opts);
//						iv.setImageBitmap(bm);
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					}
					
					// Thumbnails
//					long id = cursor.getLong(cursor.getColumnIndex(Images.Media._ID));
//					Bitmap bm = Images.Thumbnails.getThumbnail(getContentResolver(), id, Images.Thumbnails.MINI_KIND, null);
//					iv.setImageBitmap(bm);
					
					// AndroidUniversalImageLoader Using file scheme
//					String path = cursor.getString(cursor.getColumnIndex(Images.Media.DATA));
//					Uri fileUri = Uri.fromFile(new File(path));
//					ImageLoader.getInstance().displayImage(fileUri.toString(), iv);
					
					// AndroidUniversalImageLoader Using content scheme
					long id = cursor.getLong(cursor.getColumnIndex(Images.Media._ID));
					Uri contentUri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id);
					ImageLoader.getInstance().displayImage(contentUri.toString(), iv);
					
					return true;
				}
				return false;
			}
		});
		gridView.setAdapter(mAdapter);
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
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = Images.Media.EXTERNAL_CONTENT_URI;
		return new CursorLoader(this, uri, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		dataColumn = cursor.getColumnIndex(Images.Media.DATA);
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
