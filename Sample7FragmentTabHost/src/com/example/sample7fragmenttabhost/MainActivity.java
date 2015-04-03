package com.example.sample7fragmenttabhost;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

	FragmentTabHost tabHost;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        
        TabSpecItemView view = new TabSpecItemView(this);
        view.setText("TAB1");
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(view), OneFragment.class, null);
        view = new TabSpecItemView(this);
        view.setText("TAB2");
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(view), TwoFragment.class, null);
        view = new TabSpecItemView(this);
        view.setText("TAB3");
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(view), ThreeFragment.class, null);
        
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
