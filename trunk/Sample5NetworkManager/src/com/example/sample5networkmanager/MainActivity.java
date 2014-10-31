package com.example.sample5networkmanager;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample5networkmanager.NetworkManager.OnResultListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<Product> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, new ArrayList<Product>());
		listView.setAdapter(mAdapter);
		
		initData();
	}
	
	private void initData() {
		NetworkManager.getInstance().getProductList(this, 1, 1, new OnResultListener<ProductResult>() {
			
			@Override
			public void onSuccess(ProductResult result) {
				for (Product p : result.result.product) {
					mAdapter.add(p);
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
			}
		});
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
