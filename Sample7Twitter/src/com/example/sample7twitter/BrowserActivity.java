package com.example.sample7twitter;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends ActionBarActivity {

	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		webView = (WebView)findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith(MainActivity.CALLBACK_URL)) {
					Uri uri = Uri.parse(url);
					String code = uri.getQueryParameter("oauth_verifier");
					Intent data = new Intent();
					data.putExtra("code", code);
					setResult(Activity.RESULT_OK, data);
					finish();
					return true;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		
		webView.setWebChromeClient(new WebChromeClient());
		
		Intent intent = getIntent();
		Uri uri = intent.getData();
		webView.loadUrl(uri.toString());
	}

	@Override
	protected void onResume() {
		super.onResume();
		webView.resumeTimers();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		webView.pauseTimers();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browser, menu);
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
