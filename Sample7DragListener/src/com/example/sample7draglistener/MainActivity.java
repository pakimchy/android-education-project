package com.example.sample7draglistener;

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
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<String> mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setOnLongClickListener(mListener);
        tv = (TextView)findViewById(R.id.textView2);
        tv.setOnLongClickListener(mListener);
        tv = (TextView)findViewById(R.id.textView3);
        tv.setOnLongClickListener(mListener);
        
        listView.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View v, DragEvent event) {
				switch(event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED :
					return true;
				case DragEvent.ACTION_DROP :
					ClipData data = event.getClipData();
					ClipData.Item item = data.getItemAt(0);
					View source = (View)event.getLocalState();
					int x = (int)event.getX();
					int y = (int)event.getY();
					int position = listView.pointToPosition(x, y);
					if (position == ListView.INVALID_POSITION) {
						mAdapter.add(item.getText().toString());
					} else {
						mAdapter.insert(item.getText().toString(), position);
					}
					return true;
				}
				return false;
			}
		});
    }

    OnLongClickListener mListener = new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
			String tag = (String)v.getTag();
			ClipData.Item item = new ClipData.Item(tag);
			ClipData data = new ClipData(tag, new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
			DragShadowBuilder builder = new DragShadowBuilder(v);
			v.startDrag(data, builder, v, 0);
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
