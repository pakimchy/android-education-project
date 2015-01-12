package com.example.sample6draganddropold;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	DragSourceLayout dragSource;
	DragController mController;
	ListView listView;
	ArrayAdapter<String> mAdapter1;
	
	DropListView dropListView;
	ArrayAdapter<String> mAdapter2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dragSource = (DragSourceLayout)findViewById(R.id.drag_source);
		mController = new DragController(this);
		dragSource.setDragController(mController);
		
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter1);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				mController.startDrag(view, dragSource, listView.getItemAtPosition(position), DragController.DRAG_ACTION_COPY);
				return true;
			}
		});
		dropListView = (DropListView)findViewById(R.id.listView2);
		mAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		dropListView.setAdapter(mAdapter2);
		dropListView.setOnDropListener(new DropListView.OnDropListener() {
			
			@Override
			public void onDrop(Object dragInfo, int position) {
				// ...
				if (position == ListView.INVALID_POSITION) {
					mAdapter2.add((String)dragInfo);
				} else {
					mAdapter2.insert((String)dragInfo, position);
				}
			}
		});
		mController.addDropTarget(dropListView);
		
		initData();
	}
	
	private void initData() {
		for (int i = 0; i < 10; i++) {
			mAdapter1.add("item"+i);
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
