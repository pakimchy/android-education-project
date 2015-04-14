package com.example.sample7melon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.begentgroup.xmlparser.XMLParser;
import com.google.gson.Gson;

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

	EditText pageView, countView;
	ListView listView;
	ArrayAdapter<Song> mAdapter;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageView = (EditText)findViewById(R.id.edit_page);
        countView = (EditText)findViewById(R.id.edit_count);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        Button btn = (Button)findViewById(R.id.btn_get);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new MelonTask().execute();
			}
		});
    }
    
    public static final String SERVER = "http://apis.skplanetx.com";
    public static final String MELON_CHART_URL = SERVER + "/melon/charts/realtime";
    public static final String PARAMS = "count=%s&page=%s&version=1";
    class MelonTask extends AsyncTask<Void, Void, Melon> {
    	@Override
    	protected Melon doInBackground(Void... params) {
    		String count = countView.getText().toString();
    		String page = pageView.getText().toString();
    		String urlText = MELON_CHART_URL + "?" + String.format(PARAMS, count, page);
    		try {
				URL url = new URL(urlText);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestProperty("Accept", "application/xml");
				conn.setRequestProperty("appKey", "48c9c3df-13a2-34cd-ba58-4cc714287efe");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
//					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//					StringBuilder sb = new StringBuilder();
//					String line;
//					while((line=br.readLine())!= null) {
//						sb.append(line).append("\n\r");
//					}
//					String json = sb.toString();
//					JSONObject obj = new JSONObject(json);
//					MelonResult result = new MelonResult();
//					result.parseJson(obj);
//					return result;
//					Gson gson = new Gson();
//					MelonResult result = gson.fromJson(new InputStreamReader(conn.getInputStream()), MelonResult.class);
//					return result;
					XMLParser parser = new XMLParser();
					Melon result = parser.fromXml(conn.getInputStream(), "melon", Melon.class);
					return result;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
    		return null;
    	}
    	
    	@Override
    	protected void onPostExecute(Melon result) {
    		super.onPostExecute(result);
    		if (result != null) {
//    			Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
    			for (Song s : result.songs.song) {
    				mAdapter.add(s);
    			}
    		} else {
    			Toast.makeText(MainActivity.this, "error!", Toast.LENGTH_SHORT).show();
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
