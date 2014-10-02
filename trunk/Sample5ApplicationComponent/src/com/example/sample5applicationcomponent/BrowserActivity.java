package com.example.sample5applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends Activity {

	WebView webView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browser_activity);
	    webView = (WebView)findViewById(R.id.webView1);
	    webView.setWebViewClient(new WebViewClient());
	    webView.setWebChromeClient(new WebChromeClient());
	    Intent i = getIntent();
	    Uri uri = i.getData();
	    webView.loadUrl(uri.toString());
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		webView.pauseTimers();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		webView.resumeTimers();
	}

}
