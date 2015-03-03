package com.example.examplescanwifi;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<MyScanResult> mAdapter;
	WifiManager mWM;
	boolean mReenabled = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<MyScanResult>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isStart = mWM.startScan();
				if (isStart) {
					Toast.makeText(MainActivity.this,"start Scan...", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "fail start scan...", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyScanResult result = (MyScanResult)listView.getItemAtPosition(position);
				if (result.mResult != null && result.mConfiguration != null) {
					Intent intent = new Intent("com.farproc.wifi.connecter.action.CONNECT_OR_EDIT");
					intent.putExtra("com.farproc.wifi.connecter.extra.HOTSPOT", result.mResult);
//					startActivity(intent);
					WifiInfo info = mWM.getConnectionInfo();
					if (info.getNetworkId() != result.mConfiguration.networkId) {
//						connectWifi(result);
						boolean success = mWM.enableNetwork(result.mConfiguration.networkId, true);
					} else {
						Toast.makeText(MainActivity.this, "already connected", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		mWM = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		registerReceiver(mReceiver, filter);
		registerReceiver(mWifiReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
	}

	private void connectWifi(MyScanResult result) {
		result.mConfiguration.priority = getMaxPriority() + 1;
		int networkId = mWM.updateNetwork(result.mConfiguration);
		boolean b = mWM.enableNetwork(networkId, false);						
		if (b) {
			b = mWM.saveConfiguration();
			if (b) {
				WifiConfiguration conf = getConfiguration(result.mConfiguration);
				mReenabled = false;
				b = mWM.enableNetwork(conf.networkId, true);
				if (b) {
					b = mWM.reconnect();
					if (b) {
						Toast.makeText(MainActivity.this, "wifi change success", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				Toast.makeText(MainActivity.this, "reconnect fail", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(MainActivity.this, "enableNetwork fail", Toast.LENGTH_SHORT).show();
		}
	}
	
	BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if(WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
				final NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				final NetworkInfo.DetailedState detailed = networkInfo.getDetailedState();
				if(detailed != NetworkInfo.DetailedState.DISCONNECTED
						&& detailed != NetworkInfo.DetailedState.DISCONNECTING
						&& detailed != NetworkInfo.DetailedState.SCANNING) {
					if(!mReenabled) {
						mReenabled = true;
						final List<WifiConfiguration> configurations = mWM.getConfiguredNetworks();
						if(configurations != null) {
							for(final WifiConfiguration config:configurations) {
								mWM.enableNetwork(config.networkId, false);
							}
						}
						Log.i("MainActivity","receiver...");
					}
				}
			}
			
		}
	};
	
	private WifiConfiguration getConfiguration(WifiConfiguration config) {
		List<WifiConfiguration> list = mWM.getConfiguredNetworks();
		int security = getSecurity(config);
		for (WifiConfiguration conf : list) {
			if (conf.SSID.equals(config.SSID) && (getSecurity(conf) == security)) {
				return conf;
			}
		}
		return null;
	}

	private int getMaxPriority() {
		List<WifiConfiguration> list = mWM.getConfiguredNetworks();
		int max = Integer.MIN_VALUE;
		for (WifiConfiguration conf : list) {
			if (conf.priority > max) {
				max = conf.priority;
			}
		}
		if (max < 0) {
			max = 0;
		}
		return max;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
		unregisterReceiver(mWifiReceiver);
	}
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
				List<ScanResult> results = mWM.getScanResults();
				mAdapter.clear();
//				addConfiguredNetwork();
				Collections.sort(results, new Comparator<ScanResult>() {

					@Override
					public int compare(ScanResult lhs, ScanResult rhs) {
						return rhs.level - lhs.level;
					}
				});
				List<WifiConfiguration> networks = mWM.getConfiguredNetworks();				
				for (ScanResult sr : results) {
					WifiConfiguration conf = findConfiguration(sr, networks);
					if (conf == null) {
						mAdapter.add(new MyScanResult(sr));
					} else {
						mAdapter.add(new MyScanResult(sr, conf));
						networks.remove(conf);
					}
				}
				
				for (WifiConfiguration nw : networks) {
					mAdapter.add(new MyScanResult(nw));
				}
			}
		}
	};
	
	private WifiConfiguration findConfiguration(ScanResult result, List<WifiConfiguration> networks) {
		for (WifiConfiguration conf : networks) {
			if (conf.SSID.equals("\""+result.SSID+"\"") && getSecurity(result) == getSecurity(conf)) {
				return conf;
			}
		}
		return null;
	}
	
	public static final int SECURITY_NONE = 0;
	public static final int SECURITY_WEP = 1;
	public static final int SECURITY_PSK = 2;
	public static final int SECURITY_EAP = 3;
	
	private int getSecurity(ScanResult result) {
        if (result.capabilities.contains("WEP")) {
            return SECURITY_WEP;
        } else if (result.capabilities.contains("PSK")) {
            return SECURITY_PSK;
        } else if (result.capabilities.contains("EAP")) {
            return SECURITY_EAP;
        }
        return SECURITY_NONE;
	}
	
	private int getSecurity(WifiConfiguration config) {
        if (config.allowedKeyManagement.get(KeyMgmt.WPA_PSK)) {
            return SECURITY_PSK;
        }
        if (config.allowedKeyManagement.get(KeyMgmt.WPA_EAP) ||
                config.allowedKeyManagement.get(KeyMgmt.IEEE8021X)) {
            return SECURITY_EAP;
        }
        return (config.wepKeys[0] != null) ? SECURITY_WEP : SECURITY_NONE;
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
