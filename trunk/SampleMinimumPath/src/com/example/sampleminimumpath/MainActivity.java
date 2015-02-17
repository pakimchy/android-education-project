package com.example.sampleminimumpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private static final int COUNT = 10;
	Random rand = new Random();
	
	ListView listView1, listView2;
	ArrayAdapter<LineData> mAdapter1, mAdapter2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView1 = (ListView)findViewById(R.id.listView1);
		listView2 = (ListView)findViewById(R.id.listView2);
		mAdapter1 = new ArrayAdapter<LineData>(this, android.R.layout.simple_list_item_1);
		mAdapter2 = new ArrayAdapter<LineData>(this, android.R.layout.simple_list_item_1);
		listView1.setAdapter(mAdapter1);
		listView2.setAdapter(mAdapter2);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				generateRoute();
			}
		});
		
		generateRoute();
	}
	
	private void generateRoute() {
		ArrayList<LineData> source = makeData();
		ArrayList<LineData> route = searchRoute(source);
		mAdapter1.clear();
		mAdapter1.addAll(source);
		mAdapter2.clear();
		mAdapter2.addAll(route);
	}
	
	private ArrayList<LineData> makeData() {
		ArrayList<LineData> source = new ArrayList<LineData>();
		for (int i = 0; i < COUNT ; i++) {
			for (int j = i+1; j < COUNT ; j++) {
				int distance = rand.nextInt(90) + 10;
//				if (distance % 2 == 0) {
					LineData ld = new LineData();
					ld.start = i;
					ld.end = j;
					ld.distance = distance / 3;
					source.add(ld);
//				}
			}
		}
		return source;
	}
	
	private ArrayList<LineData> searchRoute(ArrayList<LineData> source) {
		ArrayList<LineData> sorted = new ArrayList<LineData>(source);
		ArrayList<LineData> route = new ArrayList<LineData>();
		int[] pointer = new int[COUNT];
		Collections.sort(sorted);
		for (int i = 0; i < sorted.size(); i++) {
			LineData ld = sorted.get(i);
			int startcount = pointer[ld.start] + 1;
			int endcount = pointer[ld.end] + 1;
			if ((ld.start == 0 || ld.start == COUNT - 1) && startcount > 1) {
				continue;
			}
			if ((ld.end == 0 || ld.end == COUNT - 1) && endcount > 1) {
				continue;
			}
			
			if (startcount > 2 || endcount > 2) {
				continue;
			}
			pointer[ld.start] = startcount;
			pointer[ld.end] = endcount;
			route.add(ld);
			if (isRoute(pointer)) {
				break;
			}
		}
		if (isRoute(pointer)) {
			
		}
		return route;
	}
	
	private boolean isRoute(int[] pointer) {
		if (pointer[0] != 1 || pointer[COUNT - 1] != 1) {
			return false;
		}
		for (int i = 1 ; i < COUNT - 2; i++) {
			if (pointer[i] != 2) {
				return false;
			}
		}
		return true;
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
