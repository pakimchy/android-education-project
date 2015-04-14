package com.example.exampletypeface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView messageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		messageView = (TextView) findViewById(R.id.text_message);
//		AssetManager am = getResources().getAssets();
//		Typeface nanum = Typeface.createFromAsset(am, "nanumgothic.ttf");
//		messageView.setTypeface(nanum);
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
	public View onCreateView(String name, @NonNull Context context,
			@NonNull AttributeSet attrs) {
    	if (name.equals("TextView")) {
//    		TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.fontFamily});
//    		String fontname = ta.getString(0);
//    		ta.recycle();
    		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCustomFont);
    		String fontname = ta.getString(R.styleable.MyCustomFont_customFont);
    		int style = ta.getInt(R.styleable.MyCustomFont_android_textStyle, Typeface.NORMAL);
    		ta.recycle();
    		Typeface font = TypefaceManager.getInstance().getTypeface(this, fontname);
    		TextView tv = (TextView)super.onCreateView(name, context, attrs);
    		if (tv == null) {
    			tv = new TextView(context, attrs);
    		}
    		if (font != null) {
    			tv.setTypeface(font,style);
    		}
    		return tv;
    	}
    	return super.onCreateView(name, context, attrs);
	}
}
