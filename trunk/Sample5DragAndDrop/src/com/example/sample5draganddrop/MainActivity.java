package com.example.sample5draganddrop;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sample5draganddrop.DropListView.OnDropListener;

public class MainActivity extends ActionBarActivity {

	ListView listView1;
	ArrayAdapter<String> mAdapter1, mAdapter2;
	DragLayout layout;
	DropListView listView2;
	DragController mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (DragLayout)findViewById(R.id.dragLayout);
		listView1 = (ListView)findViewById(R.id.listView1);
		mAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		listView1.setAdapter(mAdapter1);
		listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = (String)listView1.getItemAtPosition(position);
//				mAdapter2.add(text);
				mController.startDrag(view, layout, text, DragController.DRAG_ACTION_COPY);
				return true;
			}
		});

		listView2 = (DropListView)findViewById(R.id.listView2);
		mAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		listView2.setAdapter(mAdapter2);
		
		listView2.setOnDropListener(new OnDropListener() {
			
			@Override
			public void onDrop(Object info, int position) {
				String text = (String)info;
				if (position == AbsListView.INVALID_POSITION) {
					mAdapter2.add(text);
				} else {
					mAdapter2.insert(text, position);
				}
			}
		});
		mController = new DragController(this);
		layout.setDragController(mController);
		mController.addDropTarget(listView2);
		
		initData();
	}
	
	private void initData() {
		for (int i = 0 ; i < 10; i++) {
			mAdapter1.add("item " + i);
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
