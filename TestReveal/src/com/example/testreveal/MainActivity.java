package com.example.testreveal;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Button btn = (Button)findViewById(R.id.button1);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				int cx = (v.getWidth()) / 2;
//				int cy = (v.getHeight()) / 2;
//				int finalRadius = Math.max(v.getWidth(), v.getHeight());
//				Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
//				anim.setDuration(3000);
//				anim.start();
//			}
//		});
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ImageView iv = (ImageView)findViewById(R.id.imageView2);
				AnimatedVectorDrawable d = (AnimatedVectorDrawable)iv.getDrawable();
				d.start();
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
}
