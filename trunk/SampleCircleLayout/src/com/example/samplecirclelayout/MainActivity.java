package com.example.samplecirclelayout;

import ru.biovamp.widget.CircleLayout;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	CircleLayout circle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle = (CircleLayout)findViewById(R.id.circle);
        
        initItem();
    }
    
    private static final int MAX_DATE = 31;
    TextView[] dateView = new TextView[MAX_DATE];
    private void initItem() {
    	for (int i = 0; i < MAX_DATE; i++) {
    		TextView tv = new TextView(this);
    		tv.setText(""+(i+1));
    		tv.setId(i);
    		tv.setBackgroundColor(Color.GREEN);
    		tv.setOnClickListener(mListener);
    		circle.addView(tv);
    	}
    }
    
    OnClickListener mListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			Toast.makeText(MainActivity.this, "date : " + (id + 1), Toast.LENGTH_SHORT).show();
		}
	};
    
    


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
