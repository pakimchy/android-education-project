package com.example.sample7market;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sample7market.NetworkManager.OnResultListener;


public class MainActivity extends ActionBarActivity {

	EditText keywordView;
	ListView listView;
	ArrayAdapter<Product> mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        mAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        
        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().searchProduct(MainActivity.this, keyword, 1, 10, new OnResultListener<ProductList>() {
						
						@Override
						public void onSuccess(ProductList result) {
							for (Product p : result.productList) {
								mAdapter.add(p);
							}
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
