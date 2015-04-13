package com.example.sample7viewswitcher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	TextSwitcher textSwitcher;
	int mCount = 0;

	GestureDetector mDetector;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
        	@Override
        	public boolean onDown(MotionEvent e) {
        		return true;
        	}
        	
        	@Override
        	public boolean onFling(MotionEvent e1, MotionEvent e2,
        			float velocityX, float velocityY) {
				TextView tv = (TextView)textSwitcher.getNextView();
				tv.setText("Next Text Item : " + mCount);
				textSwitcher.showNext();
				mCount++;
        		return true;
        	}
        });
        textSwitcher = (TextSwitcher)findViewById(R.id.textSwitcher1);
        textSwitcher.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mDetector.onTouchEvent(event);
			}
		});
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tv = (TextView)textSwitcher.getNextView();
				tv.setText("Next Text Item : " + mCount);
				textSwitcher.showNext();
				mCount++;
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
