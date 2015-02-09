package com.example.sample6customfont;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	EditText editText1;
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText1 = (EditText) findViewById(R.id.editText1);
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Layout layout = editText1.getLayout();
				int height = (int) editText1.getTextSize();
				int layoutheight = (int) layout.getHeight();
				final int offset = editText1.getSelectionStart();
				final int line = layout.getLineForOffset(offset);
				final int top = layout.getLineTop(line);
				final int bottom = layout.getLineTop(line + 1);
				Log.i("MainActivity","height : " + height + "layoutheight : " + layoutheight + "," + top + "," + bottom);
				TextPaint paint = editText1.getPaint();
				FontMetrics fm = paint.getFontMetrics();
				Log.i("MainActivity", "font : " + fm.ascent + "," + fm.descent + "," + fm.top + "," + fm.bottom + "," + fm.leading);
			}
		}, 200);
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
			int[] ids = { android.R.attr.fontFamily };
			TypedArray ta = context.obtainStyledAttributes(attrs, ids);
			String fontName = ta.getString(0);
			ta.recycle();
			Typeface tf = FontManager.getInstance().getTypeface(context,
					fontName);
			if (tf != null) {
				TextView tv = new TextView(context, attrs);
				tv.setTypeface(tf);
				return tv;
			}
		}
		if (name.equals("EditText")) {
			Typeface tf = FontManager.getInstance().getTypeface(context,
					FontManager.FONT_NAME_NOTO);
			EditText editText = new EditText(context, attrs);
			editText.setTypeface(tf);
			return editText;
		}
		return super.onCreateView(name, context, attrs);
	}
}
