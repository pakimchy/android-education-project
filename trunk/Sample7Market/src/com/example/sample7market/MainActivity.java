package com.example.sample7market;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sample7market.NetworkManager.OnResultListener;


public class MainActivity extends ActionBarActivity {

	EditText keywordView;
	ListView listView;
//	ArrayAdapter<Product> mAdapter;
	MyAdapter mAdapter;
	SwipeRefreshLayout refresh;
	Handler mHandler = new Handler(Looper.getMainLooper());
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
//        mAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
        refresh = (SwipeRefreshLayout)findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        refresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						refresh.setRefreshing(false);
					}
				}, 3000);
			}
		});
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Product p = (Product)listView.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				intent.putExtra(DetailActivity.EXTRA_PRODUCT_CODE, p.productCode);
				startActivity(intent);
			}
		});
        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().searchProduct(MainActivity.this, keyword, 1, 10, new OnResultListener<ProductList>() {
						
						@Override
						public void onSuccess(ProductList result) {
//							for (Product p : result.productList) {
//								mAdapter.add(p);
//							}
							mAdapter.addAll(result.productList);
						}
						
						@Override
						public void onFail(int code) {
							
						}
					});
				}
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
