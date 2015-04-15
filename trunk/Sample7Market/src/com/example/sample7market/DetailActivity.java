package com.example.sample7market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sample7market.NetworkManager.OnResultListener;

public class DetailActivity extends ActionBarActivity {

	TextView codeView, nameView;
	public static final String EXTRA_PRODUCT_CODE = "code";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		codeView = (TextView)findViewById(R.id.text_code);
		nameView = (TextView)findViewById(R.id.text_name);
		Intent intent = getIntent();
		int code = intent.getIntExtra(EXTRA_PRODUCT_CODE, -1);
		if (code != -1) {
			NetworkManager.getInstnace().getProductDetail(this, code, new OnResultListener<ProductDetail>() {
				
				@Override
				public void onSuccess(ProductDetail result) {
					codeView.setText(""+result.productCode);
					nameView.setText(result.productName);
				}
				
				@Override
				public void onFail(int code) {
					
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
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
