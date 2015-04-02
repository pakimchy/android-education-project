package com.example.sample7fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

	FragmentManager mFragmentManager;
	Button btnTab1, btnTab2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        btnTab1 = (Button)findViewById(R.id.btn_tab1);
        btnTab1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeTab1();
			}
		});
        btnTab2 = (Button)findViewById(R.id.btn_tab2);
        btnTab2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeTab2();
			}
		});
        changeTab1();
    }
    
    private void changeTab1() {
    	FragmentTransaction ft = mFragmentManager.beginTransaction();
    	OneFragment f = new OneFragment();
    	ft.replace(R.id.container, f);
    	ft.commit();
    	btnTab2.setSelected(false);
    	btnTab1.setSelected(true);
    }
    
    private void changeTab2() {
    	FragmentTransaction ft = mFragmentManager.beginTransaction();
    	TwoFragment f = new TwoFragment();
    	ft.replace(R.id.container, f);
    	ft.commit();
    	btnTab1.setSelected(false);
    	btnTab2.setSelected(true);
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
