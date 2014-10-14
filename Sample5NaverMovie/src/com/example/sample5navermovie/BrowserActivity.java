package com.example.sample5navermovie;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends Activity {

	/** Called when the activity is first created. */
	WebView webView;
	public static final String PARAM_URL = "url";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browser_activity);
	    webView = (WebView)findViewById(R.id.webView1);
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.setWebViewClient(new WebViewClient() {
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		if (!url.startsWith("http://") && !url.startsWith("https://")) {
	    			Uri uri = Uri.parse(url);
	    			startActivity(new Intent(Intent.ACTION_VIEW, uri));
	    			return true;
	    		}
	    		return super.shouldOverrideUrlLoading(view, url);
	    	}
	    });
	    webView.setWebChromeClient(new WebChromeClient());
	    
	    Intent i = getIntent();
	    String url = i.getStringExtra(PARAM_URL);
	    if (url != null && !url.equals("")) {
	    	webView.loadUrl(url);
	    }
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
