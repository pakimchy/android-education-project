package com.example.sample7basicwidget;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TestActivity extends ActionBarActivity {

	ImageView photoView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		photoView = (ImageView)findViewById(R.id.image_photo);
		Button btn = (Button)findViewById(R.id.btn_change);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				photoView.setImageResource(R.drawable.photo1);
				
//				Drawable photo = getResources().getDrawable(R.drawable.photo1);
//				photoView.setImageDrawable(photo);
				
				InputStream is = getResources().openRawResource(R.drawable.photo1);
				
				Bitmap bm = BitmapFactory.decodeStream(is);
				photoView.setImageBitmap(bm);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
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
}
