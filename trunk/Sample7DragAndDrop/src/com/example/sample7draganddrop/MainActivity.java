package com.example.sample7draganddrop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sample7draganddrop.TargetListView.OnItemDropListener;


public class MainActivity extends ActionBarActivity {

	DragController mController;
	DragSourceLayout mDragSource;
	ListView sourceList;
	TargetListView targetList;
	ArrayAdapter<String> mSourceAdapter;
	ArrayAdapter<String> mTargetAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDragSource = (DragSourceLayout)findViewById(R.id.dragSource);
        mController = new DragController(this);
        mDragSource.setDragController(mController);
        sourceList = (ListView)findViewById(R.id.listView1);
        targetList = (TargetListView)findViewById(R.id.listView2);
        mController.addDropTarget(targetList);
        mSourceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mTargetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        sourceList.setAdapter(mSourceAdapter);
        targetList.setAdapter(mTargetAdapter);
        sourceList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				String dragInfo = (String)sourceList.getItemAtPosition(position);
				mController.startDrag(view, mDragSource, dragInfo, DragController.DRAG_ACTION_COPY);
				return true;
			}
		});
        targetList.setOnItemDropListener(new OnItemDropListener() {
			
			@Override
			public void onItemDrop(ListView listview, int position, String data) {
				if (position == ListView.INVALID_POSITION) {
					mTargetAdapter.add(data);
				} else {
					mTargetAdapter.insert(data, position);
				}
				mSourceAdapter.remove(data);
			}
		});
        initData();
    }
    
    private void initData() {
    	for (int i = 0; i < 10; i++) {
    		mSourceAdapter.add("item"+i);
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
