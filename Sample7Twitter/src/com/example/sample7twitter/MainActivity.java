package com.example.sample7twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	private static final String CONSUMER_KEY = "WkuGoKprXg0oQgQtShzsQvyzR";
	private static final String CONSUMER_SECRET = "23dhAuhXsbSzzc1BfZCvDUpUkimourTBo7QvpjlE7USMLipvsN";
	public static final String CALLBACK_URL = "http://dongjaguestbook.appspot.com/";

	Twitter mTwitter;
	Button loginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTwitter = assignTwitter();
		AccessToken token = PropertyManager.getInstance().getAccessToken();
		loginButton = (Button)findViewById(R.id.btn_login);
		if (token != null) {
			mTwitter.setOAuthAccessToken(token);
			loginButton.setEnabled(false);
		} else {
			loginButton.setEnabled(true);
		}
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new RequestLoginTask().execute();
			}
		});
	}
	
	class RequestLoginTask extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			try {
				RequestToken requestToken = mTwitter.getOAuthRequestToken(CALLBACK_URL);
				return requestToken.getAuthenticationURL();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
				intent.setData(Uri.parse(result));
				startActivityForResult(intent, 0);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			String code = data.getStringExtra("code");
			new AccessTokenTask().execute(code);
		}
	}
	
	class AccessTokenTask extends AsyncTask<String, Void, AccessToken> {
		@Override
		protected AccessToken doInBackground(String... params) {
			String code = params[0];
			try {
				AccessToken token = mTwitter.getOAuthAccessToken(code);
				PropertyManager.getInstance().setAccessToken(token);
				return token;
			} catch (TwitterException e) {
				e.printStackTrace();
			}			
			return null;
		}
		
		@Override
		protected void onPostExecute(AccessToken result) {
			super.onPostExecute(result);
			if (result != null) {
				loginButton.setEnabled(false);
			}
		}
	}
	
	private Twitter assignTwitter() {
	    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
	    configurationBuilder.setOAuthConsumerKey(CONSUMER_KEY);
	    configurationBuilder.setOAuthConsumerSecret(CONSUMER_SECRET);
	    Configuration configuration = configurationBuilder.build();
	    TwitterFactory twitterFactory = new TwitterFactory(configuration);
	    return twitterFactory.getInstance();
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
