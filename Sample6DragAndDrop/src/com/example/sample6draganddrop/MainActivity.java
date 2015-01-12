package com.example.sample6draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView tv = (TextView) findViewById(R.id.text_item1);
		tv.setTag("Item1");
		tv.setOnLongClickListener(onStartDrag);

		tv = (TextView) findViewById(R.id.text_item2);
		tv.setTag("Item2");
		tv.setOnLongClickListener(onStartDrag);

		tv = (TextView) findViewById(R.id.text_item3);
		tv.setTag("Item3");
		tv.setOnLongClickListener(onStartDrag);

		listView = (ListView) findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);

		listView.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					return true;
				case DragEvent.ACTION_DRAG_ENTERED :
				case DragEvent.ACTION_DRAG_EXITED :
				case DragEvent.ACTION_DRAG_LOCATION :
					break;
				case DragEvent.ACTION_DROP :
					String tag = event.getClipData().getItemAt(0).toString();
					int x = (int)event.getX();
					int y = (int)event.getY();
					int position = listView.pointToPosition(x, y);
					if (position == ListView.INVALID_POSITION) {
						mAdapter.add(tag);
					} else {
						mAdapter.insert(tag, position);
					}
					return true;
				case DragEvent.ACTION_DRAG_ENDED :
					if (event.getResult()) {
						Toast.makeText(MainActivity.this, "drop listview", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(MainActivity.this, "drop other", Toast.LENGTH_SHORT).show();
					}
					

				}
				return false;
			}
		});
	}

	View.OnLongClickListener onStartDrag = new View.OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			String tag = (String) v.getTag();
			ClipData.Item item = new ClipData.Item(tag);
			ClipData clip = new ClipData("drag",
					new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }, item);
			v.startDrag(clip, new DragShadowBuilder(v), null, 0);
			return true;
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
