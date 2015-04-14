package com.example.exampletwitter;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	public static final String CONSUMER_KEY="WkuGoKprXg0oQgQtShzsQvyzR";
	public static final String CONSUMER_SECRET="23dhAuhXsbSzzc1BfZCvDUpUkimourTBo7QvpjlE7USMLipvsN";
	public static final String CALLBACK_URL="http://dongjaguestbook.appspot.com/";
	Twitter twitter;
	RequestToken requestToken;
	
	ListView listView;
	EditText messageView;
	ArrayAdapter<String> mAdapter;
	boolean bLogin = false;
	
	private static final int REQUEST_CODE_BROWSER = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		messageView = (EditText)findViewById(R.id.edit_message);
		Button btn = (Button)findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!bLogin) {
					new RequestLoginBrowserTask().execute();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_timeline);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getTimeLine();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new PostStatusTask().execute(messageView.getText().toString());
			}
		});
		twitter = assignTwitter();
		AccessToken token = PropertyManager.getInstance().getAccessToken();
		if (token != null) {
			twitter.setOAuthAccessToken(token);
			bLogin = true;
			getTimeLine();
		}
	}
	
	class RequestLoginBrowserTask extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			try {
				requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
				return requestToken.getAuthenticationURL();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
				intent.putExtra(BrowserActivity.EXTRA_URL, result);
				startActivityForResult(intent, REQUEST_CODE_BROWSER);
			} else {
				Toast.makeText(MainActivity.this, "authentication url fail", Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_BROWSER && resultCode == Activity.RESULT_OK) {
			String verifier = data.getStringExtra(BrowserActivity.RESULT_PARAM_VERIFIER);
			new AccessTokenTask().execute(verifier);
		}
	}
	
	class AccessTokenTask extends AsyncTask<String, Integer, AccessToken> {
		@Override
		protected AccessToken doInBackground(String... params) {
			String verifier = params[0];
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(verifier);
				PropertyManager.getInstance().setAccessToken(accessToken);
				bLogin = true;
				return accessToken;
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(AccessToken result) {
			super.onPostExecute(result);
			if (result != null) {
				getTimeLine();
			} else {
				Toast.makeText(MainActivity.this, "access token fail", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void getTimeLine() {
		new HomeTimeLineTask().execute();
	}

	class HomeTimeLineTask extends AsyncTask<Void, Integer, List<Status>> {
		@Override
		protected List<twitter4j.Status> doInBackground(Void... params) {
			try {
				List<twitter4j.Status> list = twitter.getHomeTimeline();
				return list;
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<twitter4j.Status> result) {
			super.onPostExecute(result);
			mAdapter.clear();
			if (result != null) {
				for (twitter4j.Status status : result) {
					mAdapter.add(status.getUser().getName() + ":" +status.getText());
				}
			}
		}
	}
	
	class PostStatusTask extends AsyncTask<String, Integer, Status> {
		@Override
		protected twitter4j.Status doInBackground(String... params) {
			try {
				String message = params[0];
				twitter4j.Status status = twitter.updateStatus(message);
				return status;
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(twitter4j.Status result) {
			super.onPostExecute(result);
			if (result != null) {
				Toast.makeText(MainActivity.this, "update status", Toast.LENGTH_SHORT).show();
				getTimeLine();
			} else {
				Toast.makeText(MainActivity.this, "update status fail", Toast.LENGTH_SHORT).show();
			}
		}
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
