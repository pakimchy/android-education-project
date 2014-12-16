package com.example.sampletypefacetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	Typeface nanum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nanum = Typeface.createFromAsset(getResources().getAssets(), "nanumgothic.ttf");
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		View v = super.onCreateView(name, context, attrs);
		if (v == null && name.equals("TextView")) {
			TextView tv = new TextView(context,attrs);
			tv.setTypeface(nanum);
			v = tv;
		}
		return v;
	}
	
}
