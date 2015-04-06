package com.example.sample7actionbar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	ActionBar actionBar;
	EditText keywordView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.drawable.ic_launcher);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        ActionBar.Tab tab = actionBar.newTab();
//        tab.setText("tab1");
//        tab.setTabListener(new ActionBar.TabListener() {
//			
//			@Override
//			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
//				
//			}
//			
//			@Override
//			public void onTabSelected(Tab arg0, FragmentTransaction ft) {
//				ft.replace(R.id.container, new OneFragment());
//			}
//			
//			@Override
//			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
//				
//			}
//		});
//        actionBar.addTab(tab);
//
//        tab = actionBar.newTab();
//        tab.setText("tab2");
//        tab.setTabListener(new ActionBar.TabListener() {
//			
//			@Override
//			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
//				
//			}
//			
//			@Override
//			public void onTabSelected(Tab arg0, FragmentTransaction ft) {
//				ft.replace(R.id.container, new TwoFragment());
//			}
//			
//			@Override
//			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
//				
//			}
//		});
//        actionBar.addTab(tab);
        
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
//			
//			@Override
//			public boolean onNavigationItemSelected(int position, long id) {
//				Toast.makeText(MainActivity.this, "position : " + position, Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
//        adapter.add("item1");
//        adapter.add("item2");
//        adapter.add("item3");
//        actionBar.setDisplayShowCustomEnabled(true);
//        View view = getLayoutInflater().inflate(R.layout.title_layout, null);
//        actionBar.setCustomView(view, new ActionBar.LayoutParams(Gravity.CENTER));
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setCustomView(R.layout.action_bar_layout);
//        keywordView = (EditText)findViewById(R.id.edit_keyword);
//        keywordView.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				String text = s.toString();
//				Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//        actionBar.setHomeButtonEnabled(false);
        
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
        if (id == android.R.id.home) {
        	Toast.makeText(this, "homeAsUp Click", Toast.LENGTH_SHORT).show();
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
