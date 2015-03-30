package com.example.sample7compoundwidget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sample7compoundwidget.ImageTextView.OnImageClickListener;


public class MainActivity extends ActionBarActivity {

	ImageTextView itemView;
	ImageTextView itemView2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemView = (ImageTextView)findViewById(R.id.item_myitem);
        ItemData d = new ItemData();
        d.iconId = R.drawable.ic_launcher;
        d.title = "my item test";
        itemView.setItemData(d);
        itemView.setOnImageClickListener(listener);
        
        itemView2 = (ImageTextView)findViewById(R.id.item_myitem2);
        d = new ItemData();
        d.iconId = R.drawable.ic_launcher;
        d.title = "my item test 2";
        itemView2.setItemData(d);
        itemView2.setOnImageClickListener(listener);
    }
    
    OnImageClickListener listener = new OnImageClickListener() {
		
		@Override
		public void onImageClicked(ImageTextView view, ItemData data) {
			Toast.makeText(MainActivity.this, "title : "+data.title, Toast.LENGTH_SHORT).show();
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
