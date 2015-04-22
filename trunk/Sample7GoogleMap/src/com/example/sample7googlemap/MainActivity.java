package com.example.sample7googlemap;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample7googlemap.NetworkManager.OnResultListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity implements
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener,
	GoogleMap.OnMarkerDragListener {

	GoogleMap mMap;
	LocationManager mLM;
	String mProvider = LocationManager.GPS_PROVIDER;
	ListView listView;
	EditText keywordView;
	ArrayAdapter<POI> mAdapter;
	View markerView;
	TextView nameView, addressView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        markerView = getLayoutInflater().inflate(R.layout.info_layout, null);
        nameView= (TextView)markerView.findViewById(R.id.text_name);
        addressView = (TextView)markerView.findViewById(R.id.text_address);
        
        listView = (ListView)findViewById(R.id.listView1);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        mAdapter = new ArrayAdapter<POI>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        
        mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        setupMapIfNeeded();
        
        Button btn = (Button)findViewById(R.id.btn_marker);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraPosition position = mMap.getCameraPosition();
				LatLng latLng = position.target;
				addMarker(latLng.latitude, latLng.longitude);
			}
		});

        btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					searchPOI(keyword);
				}
			}
		});
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				POI poi = (POI)listView.getItemAtPosition(position);
				Marker marker = mMarkerResolver.get(poi);
//				marker.showInfoWindow();
				if (mAnimateRunnable != null) {
					mHandler.removeCallbacks(mAnimateRunnable);
					mAnimateRunnable = null;
				}
				mAnimateRunnable = new AnimateRunnable(marker);
				moveMap(marker.getPosition().latitude, marker.getPosition().longitude, new Runnable() {
					
					@Override
					public void run() {
						mHandler.post(mAnimateRunnable);
					}
				});
			}
		});
    }
    
    Handler mHandler = new Handler(Looper.getMainLooper());
    AnimateRunnable mAnimateRunnable = null;
    
    class AnimateRunnable implements Runnable {
    	Marker marker;
    	long startTime = -1;
    	BounceInterpolator interpolator;
    	
    	public AnimateRunnable(Marker marker) {
    		this.marker = marker;
    		interpolator = new BounceInterpolator();
    	}
    	
    	@Override
    	public void run() {
    		long currentTime = SystemClock.uptimeMillis();
    		if (startTime == -1) {
    			startTime = currentTime;
    			marker.showInfoWindow();
    		}
    		float interval = (float)(currentTime - startTime) / 2000;
    		if (interval < 1) {
    			float delta = interpolator.getInterpolation(interval);
    			marker.setAnchor(0.5f, 2-delta);
    			mHandler.postDelayed(this, 100);
    		} else {
    			marker.setAnchor(0.5f, 1);
    			mAnimateRunnable = null;
    		}    		
    	}
    }

    private void searchPOI(String keyword) {
    	NetworkManager.getInstnace().searchPOI(this, keyword, new OnResultListener<SearchPoiResult>() {
			
			@Override
			public void onSuccess(SearchPoiResult result) {
				clearPOI();
				for (POI poi : result.searchPoiInfo.pois.poiList) {
					mAdapter.add(poi);
					addMarker(poi);
				}
			}
			
			@Override
			public void onFail(int code) {
				
			}
		});
    }

    private void clearPOI() {
    	for (int i = 0; i < mAdapter.getCount(); i++) {
    		POI poi = (POI)mAdapter.getItem(i);
    		Marker marker = mMarkerResolver.get(poi);
    		marker.remove();
    	}
    	
    	mMarkerResolver.clear();
    	mPOIResolver.clear();
    	mAdapter.clear();
    }
    
    Map<POI, Marker> mMarkerResolver = new HashMap<POI, Marker>();
    Map<Marker, POI> mPOIResolver = new HashMap<Marker, POI>();

	private void addMarker(POI poi) {
		LatLng latLng = new LatLng(poi.getLatitude(), poi.getLongitude());
		MarkerOptions options = new MarkerOptions();
		options.position(latLng);
		options.title(poi.name);
		options.snippet(poi.lowerAddrName);
		
		nameView.setText(poi.name);
		addressView.setText(poi.lowerAddrName);
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(300, MeasureSpec.AT_MOST);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		markerView.measure(widthMeasureSpec, heightMeasureSpec);
		markerView.layout(0, 0, markerView.getMeasuredWidth(), markerView.getMeasuredHeight());
		Bitmap bm = getViewBitmap(markerView);

		options.icon(BitmapDescriptorFactory.fromBitmap(bm));
		options.anchor(0.5f, 1);
		options.draggable(true);
		Marker marker = mMap.addMarker(options);

		mMarkerResolver.put(poi, marker);
		mPOIResolver.put(marker, poi);
		
	}
    
    LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			moveMap(location.getLatitude(), location.getLongitude());
			mLM.removeUpdates(this);
		}
	};
	
	
	private void addMarker(double lat, double lng) {
		LatLng latLng = new LatLng(lat, lng);
		MarkerOptions options = new MarkerOptions();
		options.position(latLng);
		options.title("My Marker");
		options.snippet("my content...");
		options.icon(BitmapDescriptorFactory.defaultMarker());
		options.anchor(0.5f, 1);
		options.draggable(true);
		Marker marker = mMap.addMarker(options);
		
	}
	private void moveMap(double lat, double lng) {
		moveMap(lat, lng, null);
	}
	
	private void moveMap(double lat, double lng, final Runnable action) {
		if (mMap != null) {
			LatLng latlng = new LatLng(lat, lng);
			CameraPosition position;
			CameraPosition.Builder builder = new CameraPosition.Builder();
			builder.target(latlng);
			builder.zoom(15.5f);
//			builder.bearing(30);
//			builder.tilt(30);
			position = builder.build();
			
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
//			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, 15.5f);
			
//			update = CameraUpdateFactory.zoomIn();
			
//			mMap.moveCamera(update);
			mMap.animateCamera(update, new CancelableCallback() {
				
				@Override
				public void onFinish() {
					if (action != null) {
						action.run();
					}
				}
				
				@Override
				public void onCancel() {
					
				}
			});
			
			
		}
	}
	
    @Override
    protected void onStart() {
    	super.onStart();
    	Location location = mLM.getLastKnownLocation(mProvider);
    	if (location != null) {
    		mListener.onLocationChanged(location);
    	}
    	mLM.requestLocationUpdates(mProvider, 0, 0, mListener);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	mLM.removeUpdates(mListener);
    }
    @Override
    protected void onResume() {
    	super.onResume();
    	setupMapIfNeeded();
    }

    private void setupMapIfNeeded() {
    	if (mMap == null) {
    		mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment1)).getMap();
    		if (mMap != null) {
    			setupMap();
    		}
    	}
    }
    
    private void setupMap() {
    	mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    	mMap.setMyLocationEnabled(true);
    	
    	mMap.getUiSettings().setZoomControlsEnabled(true);
    	
    	mMap.getUiSettings().setCompassEnabled(true);
    	mMap.setOnMarkerClickListener(this);
    	mMap.setOnInfoWindowClickListener(this);
    	mMap.setOnMarkerDragListener(this);
    	mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this, mPOIResolver));
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
	@Override
	public boolean onMarkerClick(Marker marker) {
		Toast.makeText(this, "marker click", Toast.LENGTH_SHORT).show();
//		marker.showInfoWindow();
		return false;
	}
	@Override
	public void onInfoWindowClick(Marker marker) {
		POI poi = mPOIResolver.get(marker);
		if  (poi != null) {
			Toast.makeText(this, "poi : " + poi.name, Toast.LENGTH_SHORT).show();
		}
		marker.hideInfoWindow();
	}
	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	
    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
}
