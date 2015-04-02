package com.example.sample7fragmentchildfragmentmanager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

	FragmentManager mFM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFM = getSupportFragmentManager();
        Button btn = (Button)findViewById(R.id.btn_tab1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				replaceTab1();
			}
		});
        btn = (Button)findViewById(R.id.btn_tab2);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				replaceTab2();
			}
		});
        
        if (savedInstanceState == null) {
        	replaceTab1();
        }
    }

	private void replaceTab1() {
		OneFragment f = new OneFragment();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.container, f);
		ft.commit();
	}
	
	private void replaceTab2() {
		TwoFragment f = new TwoFragment();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.container, f);
		ft.commit();
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
