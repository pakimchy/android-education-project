package com.example.sample7expandablelist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;


public class MainActivity extends ActionBarActivity {

	ExpandableListView listView;
	MyAdapter mAdapter;
	
	int expandPosition = -1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ExpandableListView)findViewById(R.id.expandableListView1);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
        
        initData();
        
//        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
//        	listView.expandGroup(i);
//        }
        
        listView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				if (expandPosition == -1) {
					expandPosition = groupPosition;
				} else {
					int position = expandPosition;
					expandPosition = groupPosition;
					listView.collapseGroup(position);
				}
			}
		});
        
        listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				if (expandPosition == groupPosition) {
					expandPosition = -1;
				}
//				listView.expandGroup(groupPosition);
			}
		});
    }
    
    private void initData() {
    	ChildData cd = new ChildData();
    	cd.name = "My";
    	cd.iconId = R.drawable.ic_launcher;
    	cd.message = "My Message";
    	mAdapter.addChild("my group", cd);
    	
    	for (int i = 0 ; i < 3; i++) {
    		cd = new ChildData();
    		cd.name = "New Friend" + i;
    		cd.message = "New message " + i;
    		cd.iconId = R.drawable.ic_launcher;
    		mAdapter.addChild("new", cd);
    	}
    	
    	for (int i = 0 ; i < 5; i++) {
    		cd = new ChildData();
    		cd.name = "Favorit Friend" + i;
    		cd.message = "Favorit message " + i;
    		cd.iconId = R.drawable.ic_launcher;
    		mAdapter.addChild("favorit", cd);
    	}
    	
    	for (int i = 0 ; i < 20; i++) {
    		cd = new ChildData();
    		cd.name = "Friend" + i;
    		cd.message = "Friend message " + i;
    		cd.iconId = R.drawable.ic_launcher;
    		mAdapter.addChild("friend", cd);
    	}
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
