package com.example.sample7webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";
	EditText urlView;
	WebView webView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlView = (EditText)findViewById(R.id.edit_url);
        webView = (WebView)findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		if (!url.startsWith("http://") && !url.startsWith("https://")) {
        			Uri uri = Uri.parse(url);
        			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        			intent.addCategory(Intent.CATEGORY_BROWSABLE);
        			intent.addCategory(Intent.CATEGORY_DEFAULT);
        			startActivity(intent);  
        		}
        		return super.shouldOverrideUrlLoading(view, url);
        	}
        });
        
        webView.setWebChromeClient(new WebChromeClient(){
        	@Override
        	public void onProgressChanged(WebView view, int newProgress) {
        		super.onProgressChanged(view, newProgress);
        		Log.i(TAG, "progress : " + newProgress);
        	}
        });
        Uri data = getIntent().getData();
        String url = "http://www.naver.com";
        if (data != null && getIntent().getAction().equals(Intent.ACTION_VIEW) && data.getScheme().equals("http")) {
        	url = data.toString();
        }
        webView.loadUrl(url);
        
        Button btn = (Button)findViewById(R.id.btn_go);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = urlView.getText().toString();
				if (url != null && !url.equals("")) {
					url = url.toLowerCase();
					if (!url.startsWith("http://") && !url.startsWith("https://")) {
						url = "http://" + url;
					}
					webView.loadUrl(url);
				}
			}
		});
        
        btn = (Button)findViewById(R.id.btn_prev);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					Toast.makeText(MainActivity.this, "no history", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        btn = (Button)findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (webView.canGoForward()) {
					webView.goForward();
				} else {
					Toast.makeText(MainActivity.this, "no history", Toast.LENGTH_SHORT).show();
				}
			}
		});
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
