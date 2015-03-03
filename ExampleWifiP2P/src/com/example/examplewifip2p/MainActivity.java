package com.example.examplewifip2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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

	WifiP2pManager p2pManager;
	WifiP2pManager.Channel mChannel;
	boolean p2pEnabled = false;
	ListView listView;
	ArrayAdapter<WifiP2pDevice> mAdapter;
	ThreadPoolExecutor mClientExecutor = new ThreadPoolExecutor(1, 1, 1,
			TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<WifiP2pDevice>(this,
				android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);

		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		filter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
		filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		registerReceiver(mReceiver, filter);

		p2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		mChannel = p2pManager.initialize(this, Looper.getMainLooper(), null);

		Button btn = (Button) findViewById(R.id.btn_discovery);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startDiscovery();
			}
		});

		btn = (Button) findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				WifiP2pDevice device = (WifiP2pDevice) listView
						.getItemAtPosition(position);
				WifiP2pConfig config = new WifiP2pConfig();
				config.deviceAddress = device.deviceAddress;
				config.wps.setup = WpsInfo.PBC;
				p2pManager.connect(mChannel, config, new ActionListener() {

					@Override
					public void onSuccess() {
						Toast.makeText(MainActivity.this,
								"success p2p connection", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onFailure(int reason) {
						Toast.makeText(MainActivity.this,
								"fail p2p connection", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		});
	}

	class ClientTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			if (mInfo != null) {
				Socket s = null;
				try {
					s = new Socket(mInfo.groupOwnerAddress, SERVER_PORT);
					ObjectOutputStream oos = new ObjectOutputStream(
							s.getOutputStream());
					String message = "Hello! Wifi Direct";
					oos.writeObject(message);
					ObjectInputStream ois = new ObjectInputStream(
							s.getInputStream());
					String result = (String) ois.readObject();
					return result;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (s != null) {
						try {
							s.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(MainActivity.this, "result : " + result,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void sendMessage() {
		if (mInfo != null) {
			new ClientTask().executeOnExecutor(mClientExecutor);
		}
	}

	private void startDiscovery() {
		if (p2pEnabled) {
			p2pManager.discoverPeers(mChannel, new ActionListener() {

				@Override
				public void onSuccess() {
					Toast.makeText(MainActivity.this,
							"success start discovery", Toast.LENGTH_SHORT)
							.show();
				}

				@Override
				public void onFailure(int reason) {
					Toast.makeText(MainActivity.this, "fail start discovery",
							Toast.LENGTH_SHORT).show();
				}
			});
			updateDevice();
		}
	}

	private void updateDevice() {
		if (p2pEnabled) {
			p2pManager.requestPeers(mChannel, new PeerListListener() {

				@Override
				public void onPeersAvailable(WifiP2pDeviceList peers) {
					mAdapter.clear();
					for (WifiP2pDevice device : peers.getDeviceList()) {
						mAdapter.add(device);
					}
				}
			});
		}
	}

	WifiP2pInfo mInfo;

	public void getWifiP2pInfo() {
		p2pManager.requestConnectionInfo(mChannel,
				new ConnectionInfoListener() {

					@Override
					public void onConnectionInfoAvailable(WifiP2pInfo info) {
						mInfo = info;
					}
				});
	}

	BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)) {
				int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,
						-1);
				if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
					Toast.makeText(context, "wifi p2p enabled",
							Toast.LENGTH_SHORT).show();
					mServerTask = new ServerTask();
					mServerTask.execute();
					p2pEnabled = true;
				} else if (state == WifiP2pManager.WIFI_P2P_STATE_DISABLED) {
					Toast.makeText(context, "wifi p2p disabled",
							Toast.LENGTH_SHORT).show();
					if (mServerTask != null) {
						mServerTask.cancel(true);
						mServerTask = null;
					}
					p2pEnabled = false;
				}
			} else if (intent.getAction().equals(
					WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)) {
				updateDevice();
			} else if (intent.getAction().equals(
					WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION)) {
				int state = intent.getIntExtra(
						WifiP2pManager.EXTRA_DISCOVERY_STATE, -1);
				if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
					Toast.makeText(context, "discovery started",
							Toast.LENGTH_SHORT).show();
				} else if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED) {
					Toast.makeText(context, "discovery stopped",
							Toast.LENGTH_SHORT).show();
				}
			} else if (intent.getAction().equals(
					WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)) {
				NetworkInfo ninfo = intent
						.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
				if (ninfo.isConnected()) {
					getWifiP2pInfo();
					Toast.makeText(context, "p2p connected", Toast.LENGTH_SHORT)
							.show();
				} else {
					mInfo = null;
				}
			}
		}
	};

	public static final int SERVER_PORT = 60010;

	public ServerTask mServerTask;

	class ServerTask extends AsyncTask<String, String, Boolean> {
		ServerSocket mServerSocket;

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				mServerSocket = new ServerSocket(SERVER_PORT);
				while (true) {
					Socket s = mServerSocket.accept();
					ObjectInputStream ois = new ObjectInputStream(
							s.getInputStream());
					String text = (String) ois.readObject();
					publishProgress(text);
					ObjectOutputStream oos = new ObjectOutputStream(
							s.getOutputStream());
					String result = "echo : " + text;
					oos.writeObject(result);
					oos.flush();
					s.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (mServerSocket != null) {
					try {
						mServerSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return false;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			try {
				mServerSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			Toast.makeText(MainActivity.this, "message : " + values[0],
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			Toast.makeText(MainActivity.this, "ServerSocket closed",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
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
