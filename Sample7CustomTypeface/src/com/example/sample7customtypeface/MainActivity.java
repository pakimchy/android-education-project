package com.example.sample7customtypeface;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView tv = (TextView)findViewById(R.id.textView2);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "nanumgothic.ttf");
//        tv.setTypeface(FontManager.getInstance().getFont(this, FontManager.FONT_NAMUM));
    }

    @Override
    public View onCreateView(String name, @NonNull Context context,
    		@NonNull AttributeSet attrs) {
    	if (name.equals("TextView")) {
    		Typeface namum = FontManager.getInstance().getFont(this, FontManager.FONT_NAMUM);
    		TextView tv = (TextView)super.onCreateView(name, context, attrs);
    		if (tv == null) {
    			tv = new TextView(context, attrs);
    		}
    		tv.setTypeface(namum);
    		return tv;
    	}
    	return super.onCreateView(name, context, attrs);
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
