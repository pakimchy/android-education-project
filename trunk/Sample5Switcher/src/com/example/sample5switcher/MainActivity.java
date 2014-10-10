package com.example.sample5switcher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	ImageSwitcher switcher;
	int[] imageIds = {R.drawable.gallery_photo_1 , 
			R.drawable.gallery_photo_2 ,
			R.drawable.gallery_photo_3 ,
			R.drawable.gallery_photo_4 ,
			R.drawable.gallery_photo_5 ,
			R.drawable.gallery_photo_6 ,
			R.drawable.gallery_photo_7 ,
			R.drawable.gallery_photo_8 
	};
	
	
	int current = 1;
	
	int textIndex = 0;
	TextSwitcher textSwitcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		switcher = (ImageSwitcher)findViewById(R.id.imageSwitcher1);
		textSwitcher = (TextSwitcher)findViewById(R.id.textSwitcher1);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				current = (current + 1) % imageIds.length;
				ImageView iv = (ImageView)switcher.getNextView();
				iv.setImageResource(imageIds[current]);
				switcher.showNext();
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv = (TextView)textSwitcher.getNextView();
				tv.setText("text " + textIndex++);
				textSwitcher.showNext();
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
