package com.example.examplensd;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	NsdManager nsdManager;
	ListView listView;
	ArrayAdapter<NsdServiceInfo> mAdapter;
	boolean bRegistered = false;
	boolean bStartDiscovery = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		IntentFilter filter = new IntentFilter(NsdManager.ACTION_NSD_STATE_CHANGED);
		registerReceiver(mReceiver, filter);
		
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<NsdServiceInfo>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		
		nsdManager = (NsdManager)getSystemService(Context.NSD_SERVICE);
		
		Button btn = (Button)findViewById(R.id.btn_register);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (bRegistered) return;
				new Thread(new Runnable() {
					public void run() {
						try {
							NsdServiceInfo serviceInfo = new NsdServiceInfo();
							serviceInfo.setServiceName("MyNsdService");
							serviceInfo.setServiceType("_http._tcp");
							serviceInfo.setHost(InetAddress.getLocalHost());
							serviceInfo.setPort(60003);
							nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				
				
			}
		});

		btn = (Button)findViewById(R.id.btn_discovery);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nsdManager.discoverServices("_http._tcp", NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NsdServiceInfo info = (NsdServiceInfo)listView.getItemAtPosition(position);
				nsdManager.resolveService(info, new NsdManager.ResolveListener() {
					
					@Override
					public void onServiceResolved(NsdServiceInfo serviceInfo) {
						Toast.makeText(MainActivity.this, "resolve success : " + serviceInfo.getHost().getHostAddress() + "," + serviceInfo.getPort(), Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
						Toast.makeText(MainActivity.this, "resolve fail : " + errorCode, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	
	private NsdServiceInfo findService(NsdServiceInfo serviceInfo) {
		for (int i = 0; i < mAdapter.getCount(); i++) {
			NsdServiceInfo info = mAdapter.getItem(i);
			if (serviceInfo.getServiceName().equals(info.getServiceName())) {
				return info;
			}
		}
		return null;
	}
	
	NsdManager.DiscoveryListener mDiscoveryListener = new NsdManager.DiscoveryListener() {
		
		@Override
		public void onStopDiscoveryFailed(String serviceType, int errorCode) {
			
		}
		
		@Override
		public void onStartDiscoveryFailed(String serviceType, int errorCode) {
			
		}
		
		@Override
		public void onServiceLost(final NsdServiceInfo serviceInfo) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					NsdServiceInfo found = findService(serviceInfo);
					if (found != null) {
						mAdapter.remove(found);
					}
				}
			});
		}
		
		@Override
		public void onServiceFound(final NsdServiceInfo serviceInfo) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					NsdServiceInfo found = findService(serviceInfo);
					if (found == null) {
						mAdapter.add(serviceInfo);
					}
				}
			});
		}
		
		@Override
		public void onDiscoveryStopped(String serviceType) {
			bStartDiscovery = false;
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(MainActivity.this, "onDiscoveryStopped", Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		@Override
		public void onDiscoveryStarted(String serviceType) {
			bStartDiscovery = true;
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(MainActivity.this, "onDiscoveryStarted", Toast.LENGTH_SHORT).show();
					mAdapter.clear();
				}
			});
		}
	};
	
	NsdManager.RegistrationListener mRegistrationListener = new NsdManager.RegistrationListener() {
		
		@Override
		public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
			
		}
		
		@Override
		public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
			Toast.makeText(MainActivity.this, "service unregistered", Toast.LENGTH_SHORT).show();
			bRegistered = false;
		}
		
		@Override
		public void onServiceRegistered(NsdServiceInfo serviceInfo) {
			Toast.makeText(MainActivity.this, "service registered", Toast.LENGTH_SHORT).show();
			bRegistered = true;
		}
		
		@Override
		public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
			
		}
	};
	
	boolean bNsdEnabled = false;
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(NsdManager.ACTION_NSD_STATE_CHANGED)) {
				int state = intent.getIntExtra(NsdManager.EXTRA_NSD_STATE, -1);
				if (state == NsdManager.NSD_STATE_ENABLED) {
					Toast.makeText(MainActivity.this, "Nsd Enabled", Toast.LENGTH_SHORT).show();
					bNsdEnabled = true;
				} else if (state == NsdManager.NSD_STATE_DISABLED) {
					Toast.makeText(MainActivity.this, "Nsd Disabled", Toast.LENGTH_SHORT).show();
					bNsdEnabled = false;
				}
			}
		}
	};

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
		if (bRegistered) {
			nsdManager.unregisterService(mRegistrationListener);
		}
		if (bStartDiscovery) {
			nsdManager.stopServiceDiscovery(mDiscoveryListener);
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
