package com.example.sample5tmap;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapData.TMapPathType;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnApiKeyListenerCallback;

public class MainActivity extends ActionBarActivity {

	TMapView mMapView;
	LocationManager mLM;
	String mProvider = LocationManager.GPS_PROVIDER;
	ListView listView;
	ArrayAdapter<ListItem> mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		keywordView = (EditText)findViewById(R.id.keyword);
		mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<ListItem>());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListItem item = (ListItem)listView.getItemAtPosition(position);
				moveMap(item.item.getPOIPoint().getLatitude(), item.item.getPOIPoint().getLongitude());
			}
		});
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mMapView = (TMapView)findViewById(R.id.mapView);
		
		mMapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
		
		mMapView.setOnApiKeyListener(new OnApiKeyListenerCallback() {
			
			@Override
			public void SKPMapApikeySucceed() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "api setup success", Toast.LENGTH_SHORT).show();
						setupMap();
					}
				});
			}
			
			@Override
			public void SKPMapApikeyFailed(String arg0) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "api setup fail", Toast.LENGTH_SHORT).show();						
					}
				});
			}
		});
		
		Button btn = (Button)findViewById(R.id.btn_zoom_in);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMapView.MapZoomIn();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_zoom_out);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMapView.MapZoomOut();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_add_marker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TMapPoint point = mMapView.getCenterPoint();
				addMarker(point.getLatitude(), point.getLongitude());
			}
		});
		
		btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					TMapData data = new TMapData();
					data.findAllPOI(keyword, new FindAllPOIListenerCallback() {
						
						@Override
						public void onFindAllPOI(final ArrayList<TMapPOIItem> poilist) {
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									for (int i = 0; i < mAdapter.getCount(); i++) {
										ListItem item = mAdapter.getItem(i);
										mMapView.removeMarkerItem(item.id);
									}
									mAdapter.clear();
									for (TMapPOIItem poi : poilist) {
										ListItem item = new ListItem();
										item.item = poi;
										mAdapter.add(item);
										addMarker(item);
									}
								}
							});
						}
					});
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_route);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (startItem != null && endItem != null) {
					TMapData data = new TMapData();
					data.findPathDataWithType(TMapPathType.CAR_PATH, startItem.getTMapPoint(), endItem.getTMapPoint(), new TMapData.FindPathDataListenerCallback() {
						
						@Override
						public void onFindPathData(final TMapPolyLine path) {
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									mMapView.addTMapPath(path);
									moveMap(startItem.getTMapPoint().getLatitude(), startItem.getTMapPoint().getLongitude());
//									mMapView.setTMapPathIcon(start, end)
								}
							});
						}
					});
				} else {
					Toast.makeText(MainActivity.this, "start or end is null", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	int id = 0;
	private void addMarker(double lat, double lng) {
		TMapMarkerItem item = new TMapMarkerItem();
		Bitmap bm = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_alert)).getBitmap();
		item.setIcon(bm);
		item.setTMapPoint(new TMapPoint(lat, lng));
		item.setPosition(0.5f, 1);
		item.setCalloutTitle("marker");
		item.setCalloutSubTitle("my icon");
		Bitmap left = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_email)).getBitmap();
		Bitmap right = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_map)).getBitmap();
		item.setCalloutLeftImage(left);
		item.setCalloutRightButtonImage(right);
		item.setCanShowCallout(true);
		
		mMapView.addMarkerItem("marker" + id++, item);
	}

	private int poiIndex = 0;
	private void addMarker(ListItem list) {
		TMapMarkerItem item = new TMapMarkerItem();
		Bitmap bm = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_alert)).getBitmap();
		item.setIcon(bm);
		item.setTMapPoint(list.item.getPOIPoint());
		item.setPosition(0.5f, 1);
		item.setCalloutTitle(list.item.getPOIName());
		item.setCalloutSubTitle(list.item.getPOIContent());
		Bitmap left = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_email)).getBitmap();
		Bitmap right = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_map)).getBitmap();
		item.setCalloutLeftImage(left);
		item.setCalloutRightButtonImage(right);
		item.setCanShowCallout(true);
		list.id = "poi" + poiIndex++;
		mMapView.addMarkerItem(list.id, item);
	}
	
	private 
	
	LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			mLM.removeUpdates(mListener);
			if (isInitialized) {
				moveMap(location);
			} else {
				cacheLocation = location;
			}
		}
	};
	
	Location cacheLocation = null;
	
	private void moveMap(Location location) {
		mMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	private void moveMap(double lat, double lng) {
		mMapView.setCenterPoint(lng, lat);
	}
	
	private void setMyLocation(double lat, double lng) {
		mMapView.setLocationPoint(lng, lat);
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mMapView.setIcon(icon);
		mMapView.setIconVisibility(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 10000, 0, mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
	boolean isInitialized = false;
	TMapMarkerItem startItem;
	TMapMarkerItem endItem;
	
	private void setupMap() {
		isInitialized = true;
		mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
		mMapView.setMapType(mMapView.MAPTYPE_STANDARD);
//		mMapView.setTrafficInfo(true);
//		mMapView.setCompassMode(true);
//		mMapView.setTrackingMode(true);
		setMyLocation(37.46628337, 126.9605881);
		
		mMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
			
			@Override
			public void onCalloutRightButton(final TMapMarkerItem item) {				
				Toast.makeText(MainActivity.this, "item : " + item.getID(), Toast.LENGTH_SHORT).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("Select");
				builder.setMessage("start or end");
				builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startItem = item;
					}
				});
				builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				builder.setNegativeButton("End", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						endItem = item;
					}
				});
				
				builder.create().show();
			}
		});
		if (cacheLocation != null) {
			moveMap(cacheLocation);
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
