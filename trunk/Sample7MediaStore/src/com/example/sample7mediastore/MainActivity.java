package com.example.sample7mediastore;

import java.io.File;

import com.nostra13.universalimageloader.core.ImageLoader;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{

	GridView gridView;
	SimpleCursorAdapter mAdapter;
	int dataIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView)findViewById(R.id.gridView1);
        
        String[] from = {Images.Media.DISPLAY_NAME, Images.Media.DATA};
        int[] to = {R.id.text_name, R.id.image_content};
        mAdapter = new SimpleCursorAdapter(this, R.layout.item_images, null, from, to, 0);
        mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == dataIndex) {
					ImageView iv = (ImageView)view;
					String path = cursor.getString(columnIndex);
//					BitmapFactory.Options opts = new BitmapFactory.Options();
//					opts.inSampleSize = 4;
//					Bitmap bm = BitmapFactory.decodeFile(path, opts);
//					long id = cursor.getLong(columnIndex);
//					Bitmap bm = Images.Thumbnails.getThumbnail(getContentResolver(), id, Images.Thumbnails.MINI_KIND, null);
//					iv.setImageBitmap(bm);
					Uri uri = Uri.fromFile(new File(path));
					ImageLoader.getInstance().displayImage(uri.toString(), iv);
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
