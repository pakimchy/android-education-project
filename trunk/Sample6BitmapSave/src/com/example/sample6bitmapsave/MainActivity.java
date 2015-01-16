package com.example.sample6bitmapsave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	View captureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		captureView = findViewById(R.id.view_capture);
		Button btn = (Button) findViewById(R.id.btn_capture);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Bitmap bm = getViewBitmap(captureView);
				File saveFile = new File(Environment
						.getExternalStorageDirectory(), "a_"
						+ System.currentTimeMillis() + ".jpg");
				try {
//					FileOutputStream fos = new FileOutputStream(saveFile);
					FileOutputStream fos = MainActivity.this.openFileOutput("a.jpg", Context.MODE_PRIVATE);
					bm.compress(CompressFormat.JPEG, 100, fos);
					fos.close();
//					String url = Images.Media.insertImage(getContentResolver(), saveFile.getAbsolutePath(), "capture image", "my capture file...");
//					Toast.makeText(MainActivity.this, "url : " + url, Toast.LENGTH_SHORT).show();
//					Uri uri = Uri.parse(url);
//					ContentValues values = new ContentValues();
//					values.put(Images.Media.TITLE, "capture bitmap");
//					values.put(Images.Media.DATE_ADDED,
//							System.currentTimeMillis() / 1000);
//					values.put(Images.Media.DISPLAY_NAME, "capture bitmap");
//					values.put(Images.Media.HEIGHT, bm.getHeight());
//					values.put(Images.Media.WIDTH, bm.getWidth());
//					values.put(Images.Media.MIME_TYPE, "image/jpg");
//					Uri uri = getContentResolver().insert(
//							Images.Media.EXTERNAL_CONTENT_URI, values);
//					if (uri != null) {
//						Intent intent = new Intent(
//								Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//						intent.setDataAndType(uri, "image/jpg");
//						sendBroadcast(intent);
//					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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

	private Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

}
