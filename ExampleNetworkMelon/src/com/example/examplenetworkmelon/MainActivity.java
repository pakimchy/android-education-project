package com.example.examplenetworkmelon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examplenetworkmelon.NetworkManager.OnResultListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
//	ArrayAdapter<Song> mAdapter;
	ProductAdapter mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		listView = (ListView)findViewById(R.id.listView1);
//		mAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1);
		mAdapter = new ProductAdapter(this);
		listView.setAdapter(mAdapter);
		Button btn = (Button)findViewById(R.id.btn_melon);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				new MelonXMLTask().execute();
//				requestMelon();
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					requestProduct(keyword);
				}
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImageCache.getInstance().flush();
		ImageCache.getInstance().close();
	}
	
	private void requestProduct(String keyword) {
		NetworkRequest request = RequestFactory.getSearchProductRequest(keyword);
		NetworkManager.getInstance().getNetworkData(this, request, new OnResultListener<ProductSearchResponse>() {

			@Override
			public void onSuccess(
					NetworkRequest<ProductSearchResponse> request,
					ProductSearchResponse result) {
				mAdapter.addAll(result.products.productList);
			}

			@Override
			public void onFail(NetworkRequest<ProductSearchResponse> request,
					int code) {
				Toast.makeText(MainActivity.this, "fail...", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void requestMelon() {
		NetworkRequest request = RequestFactory.getMelonRequest();
		NetworkManager.getInstance().getNetworkData(this, request, new OnResultListener<Melon>() {

			@Override
			public void onSuccess(NetworkRequest<Melon> request,
					Melon result) {
				if (result != null) {
					for (Song s : result.songs.song) {
//						mAdapter.add(s);
					}
				}
			}

			@Override
			public void onFail(NetworkRequest<Melon> request, int code) {
				Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private static final String SERVER = "http://apis.skplanetx.com";
	private static final String MELON_CHART_URL = SERVER + "/melon/charts/realtime";
	private static final String MELON_CHART_PARAMS = "?version=%s&page=%s&count=%s";
	private static final String HEADER_ACCEPT_XML = "application/xml";
	private static final String HEADER_APPKEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	class MelonXMLTask extends AsyncTask<String, Integer, Melon> {
		@Override
		protected Melon doInBackground(String... params) {
			int version = 1;
			int page = 1;
			int count = 10;
			String urlText = MELON_CHART_URL + String.format(MELON_CHART_PARAMS, version, page, count);
			try {
				URL url = new URL(urlText);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				conn.setRequestProperty("Accept", HEADER_ACCEPT_XML);
				conn.setRequestProperty("appKey", HEADER_APPKEY);
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser parser = factory.newSAXParser();
					Melon melon = new Melon();
					XMLParserHandler handler = new XMLParserHandler("melon", melon);
					parser.parse(is, handler);
					return melon;
				}
			} catch (IOException | ParserConfigurationException | SAXException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Melon result) {
			super.onPostExecute(result);
			if (result != null) {
				for (Song s : result.songs.song) {
//					mAdapter.add(s);
				}
			}
		}
	}

	private static final String HEADER_ACCEPT_JSON = "application/json";
	class MelonJSONTask extends AsyncTask<String, Integer, Melon> {
		@Override
		protected Melon doInBackground(String... params) {
			int version = 1;
			int page = 1;
			int count = 10;
			String urlText = MELON_CHART_URL + String.format(MELON_CHART_PARAMS, version, page, count);
			try {
				URL url = new URL(urlText);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				conn.setRequestProperty("Accept", HEADER_ACCEPT_JSON);
				conn.setRequestProperty("appKey", HEADER_APPKEY);
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while((line=br.readLine())!= null) {
						sb.append(line + "\r\n");
					}
					MelonResult melonResult = new MelonResult();
					JSONObject jobject = new JSONObject(sb.toString());
					melonResult.setObject(jobject);
					return melonResult.melon;
				}
			} catch (IOException | JSONException e) {
				e.printStackTrace();
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Melon result) {
			super.onPostExecute(result);
			if (result != null) {
				for (Song s : result.songs.song) {
//					mAdapter.add(s);
				}
			}
		}
	}

	private void readStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		final StringBuilder sb = new StringBuilder();
		String line;
		while((line=br.readLine()) != null) {
			sb.append(line + "\r\n");
		}
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, "data : " + sb.toString(), Toast.LENGTH_SHORT).show();
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
