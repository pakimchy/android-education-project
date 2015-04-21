package com.example.sample7tmap;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.skp.Tmap.TMapData.FindAroundKeywordPOIListenerCallback;
import com.skp.Tmap.TMapData.FindPathDataListenerCallback;
import com.skp.Tmap.TMapData.TMapPathType;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnCalloutRightButtonClickCallback;


public class MainActivity extends ActionBarActivity {

	TMapView mapView;
	LocationManager mLM;
	String mProvider = LocationManager.GPS_PROVIDER;
	ListView listView;
	ArrayAdapter<POIData> mAdapter;
	EditText keywordView;
	TMapPoint startPoint, endPoint;
	RadioGroup group;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        group = (RadioGroup)findViewById(R.id.radioGroup1);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        mapView = (TMapView)findViewById(R.id.mapview);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new ArrayAdapter<POIData>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        new MapInitTask().execute();
        
        Button btn = (Button)findViewById(R.id.btn_marker);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addMarker();
			}
		});
        
        btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
//					searchPOI(keyword);
					searchAroundPOI(keyword);
				}
			}
		});
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				POIData data = (POIData)listView.getItemAtPosition(position);
				TMapPoint point = data.poi.getPOIPoint();
				moveMap(point.getLatitude(), point.getLongitude());
			}
		});
        
        btn = (Button)findViewById(R.id.btn_route);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (startPoint != null && endPoint != null) {
					route(startPoint, endPoint);
					startPoint = null;
					endPoint = null;
				} else {
					Toast.makeText(MainActivity.this, "start or end not setting", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }
    
    private void route(TMapPoint start, TMapPoint end) {
    	TMapData data = new TMapData();
    	data.findPathDataWithType(TMapPathType.CAR_PATH, start, end, new FindPathDataListenerCallback() {
			
			@Override
			public void onFindPathData(final TMapPolyLine path) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						path.setLineColor(Color.RED);
						path.setLineWidth(5);
						mapView.addTMapPath(path);
					}
				});
			}
		});
    }
    private void searchAroundPOI(String keyword) {
    	TMapData data = new TMapData();
    	TMapPoint point = mapView.getCenterPoint();
    	data.findAroundKeywordPOI(point, keyword, 33, 10, new FindAroundKeywordPOIListenerCallback() {
			
			@Override
			public void onFindAroundKeywordPOI(final ArrayList<TMapPOIItem> poilist) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						addPOI(poilist);
					}
				});				
			}
		});
    }
    
    private void searchPOI(String keyword) {
    	TMapData data = new TMapData();
    	data.findAllPOI(keyword, new FindAllPOIListenerCallback() {
			
			@Override
			public void onFindAllPOI(final ArrayList<TMapPOIItem> poilist) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						addPOI(poilist);
					}
				});
			}
		});
    }

    private void addPOI(ArrayList<TMapPOIItem> poilist) {
		mAdapter.clear();
		TMapPOIItem end = null;
		for (TMapPOIItem item : poilist) {
			POIData data = new POIData();
			data.poi = item;
			mAdapter.add(data);
			addMarker(item);
			end = item;
		}				
		if (end != null) {
			moveMap(end.getPOIPoint().getLatitude(), end.getPOIPoint().getLongitude());
		}    	
    }
    private void addMarker(TMapPOIItem poi) {
    	TMapMarkerItem item = new TMapMarkerItem();
    	
    	item.setTMapPoint(poi.getPOIPoint());
    	Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_happy)).getBitmap();
    	item.setIcon(icon);
    	item.setCalloutTitle(poi.getPOIName());
    	item.setCalloutSubTitle(poi.getPOIAddress());
    	Bitmap left = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_neutral)).getBitmap();
    	item.setCalloutLeftImage(left);
    	
    	Bitmap right = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_sad)).getBitmap();
    	item.setCalloutRightButtonImage(right);
    	
    	item.setCanShowCallout(true);
    	
    	mapView.addMarkerItem(poi.getPOIID(), item);
    	
    }
    
    private void addMarker() {
    	TMapPoint pt = mapView.getCenterPoint();
    	TMapMarkerItem item = new TMapMarkerItem();
    	
    	TMapPoint pp = new TMapPoint(pt.getLatitude(), pt.getLongitude());
    	item.setTMapPoint(pp);
    	Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_happy)).getBitmap();
    	item.setIcon(icon);
    	item.setCalloutTitle("my marker");
    	item.setCalloutSubTitle("content...");
    	Bitmap left = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_neutral)).getBitmap();
    	item.setCalloutLeftImage(left);
    	
    	Bitmap right = ((BitmapDrawable)getResources().getDrawable(R.drawable.stat_sad)).getBitmap();
    	item.setCalloutRightButtonImage(right);
    	
    	item.setCanShowCallout(true);
    	
    	mapView.addMarkerItem("marker1", item);
    	
    }
    
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
			if (!isInitiaized) {
				cacheLocation = location;
			} else {
				moveMap(location.getLatitude(),location.getLongitude());
				setMyLocation(location.getLatitude(), location.getLongitude());
			}
			
			mLM.removeUpdates(this);
		}
	};

	private void moveMap(double lat, double lng) {
		mapView.setCenterPoint(lng, lat);
		mapView.setZoomLevel(17);
	}
	
	private void setMyLocation(double lat, double lng) {
		mapView.setLocationPoint(lng, lat);
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mapView.setIcon(icon);
		mapView.setIconVisibility(true);
	}
	
	Location cacheLocation = null;
	
	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 0, 0,  mListener);
	}
    
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
    class MapInitTask extends AsyncTask<Void, Integer, Boolean> {
    	@Override
    	protected Boolean doInBackground(Void... params) {
    		mapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
    		mapView.setLanguage(TMapView.LANGUAGE_KOREAN);
    		return true;
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean result) {
    		super.onPostExecute(result);
    		setupMap();
    	}
    }
    
    boolean isInitiaized = false;
    private void setupMap() {
    	isInitiaized = true;
    	mapView.setMapType(TMapView.MAPTYPE_STANDARD);
    	mapView.setTrafficInfo(true);
    	mapView.setOnCalloutRightButtonClickListener(new OnCalloutRightButtonClickCallback() {
			
			@Override
			public void onCalloutRightButton(TMapMarkerItem item) {
				String id = item.getID();
				switch(group.getCheckedRadioButtonId()) {
				case R.id.radio_start :
					startPoint = item.getTMapPoint();
					Toast.makeText(MainActivity.this, "set start point " + id, Toast.LENGTH_SHORT).show();
					break;
				case R.id.radio_end :
					endPoint = item.getTMapPoint();
					Toast.makeText(MainActivity.this, "set end point " + id, Toast.LENGTH_SHORT).show();
					break;
				}				
			}
		});

    	
//    	mapView.setOnClickListenerCallBack(new OnClickListenerCallback() {
//			
//			@Override
//			public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markerlist,
//					ArrayList<TMapPOIItem> poilist, TMapPoint point, PointF pt) {
//				Toast.makeText(MainActivity.this, "onPressUpEvent", Toast.LENGTH_SHORT).show();
//				return false;
//			}
//			
//			@Override
//			public boolean onPressEvent(ArrayList<TMapMarkerItem> arg0,
//					ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
//				Toast.makeText(MainActivity.this, "onPressEvent", Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
    	if (cacheLocation != null) {
    		moveMap(cacheLocation.getLatitude(), cacheLocation.getLongitude());
    		setMyLocation(cacheLocation.getLatitude(), cacheLocation.getLongitude());
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
