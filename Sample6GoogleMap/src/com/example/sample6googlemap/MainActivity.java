package com.example.sample6googlemap;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample6googlemap.NetworkManager.OnResultListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MainActivity extends ActionBarActivity implements
	GoogleMap.OnCameraChangeListener,
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener {

	GoogleMap mMap;

	LocationManager mLM;
	
	HashMap<POI, Marker> markerResolver = new HashMap<POI, Marker>();
	HashMap<Marker, POI> poiResolver = new HashMap<Marker, POI>();
	EditText keywordView;
	ListView listView;
	ArrayAdapter<POI> mAdapter;

	View markerText;
	TextView textView;
	RadioGroup group;

	private String urls[] = { 
			"http://farm7.staticflickr.com/6101/6853156632_6374976d38_c.jpg",
			"http://farm8.staticflickr.com/7232/6913504132_a0fce67a0e_c.jpg",
			"http://farm5.staticflickr.com/4133/5096108108_df62764fcc_b.jpg",
			"http://farm5.staticflickr.com/4074/4789681330_2e30dfcacb_b.jpg",
			"http://farm9.staticflickr.com/8208/8219397252_a04e2184b2.jpg",
			"http://farm9.staticflickr.com/8483/8218023445_02037c8fda.jpg",
			"http://farm9.staticflickr.com/8335/8144074340_38a4c622ab.jpg",
			"http://farm9.staticflickr.com/8060/8173387478_a117990661.jpg",
			"http://farm9.staticflickr.com/8056/8144042175_28c3564cd3.jpg",
			"http://farm9.staticflickr.com/8183/8088373701_c9281fc202.jpg",
			"http://farm9.staticflickr.com/8185/8081514424_270630b7a5.jpg",
			"http://farm9.staticflickr.com/8462/8005636463_0cb4ea6be2.jpg",
			"http://farm9.staticflickr.com/8306/7987149886_6535bf7055.jpg",
			"http://farm9.staticflickr.com/8444/7947923460_18ffdce3a5.jpg",
			"http://farm9.staticflickr.com/8182/7941954368_3c88ba4a28.jpg",
			"http://farm9.staticflickr.com/8304/7832284992_244762c43d.jpg",
			"http://farm9.staticflickr.com/8163/7709112696_3c7149a90a.jpg",
			"http://farm8.staticflickr.com/7127/7675112872_e92b1dbe35.jpg",
			"http://farm8.staticflickr.com/7111/7429651528_a23ebb0b8c.jpg",
			"http://farm9.staticflickr.com/8288/7525381378_aa2917fa0e.jpg",
			"http://farm6.staticflickr.com/5336/7384863678_5ef87814fe.jpg",
			"http://farm8.staticflickr.com/7102/7179457127_36e1cbaab7.jpg",
			"http://farm8.staticflickr.com/7086/7238812536_1334d78c05.jpg",
			"http://farm8.staticflickr.com/7243/7193236466_33a37765a4.jpg",
			"http://farm8.staticflickr.com/7251/7059629417_e0e96a4c46.jpg",
			"http://farm8.staticflickr.com/7084/6885444694_6272874cfc.jpg"
	};
	
	ImageLoader mLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupMapIfNeeded();
		group = (RadioGroup)findViewById(R.id.radioGroup1);
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mLoader = ImageLoader.getInstance();
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<POI>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		
		markerText = getLayoutInflater().inflate(R.layout.marker_layout, null);
		textView = (TextView)markerText.findViewById(R.id.text_name);
		Button btn = (Button)findViewById(R.id.btn_zoom_in);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraUpdate update = CameraUpdateFactory.zoomIn();
				mMap.animateCamera(update);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_zoom_out);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraUpdate update = CameraUpdateFactory.zoomOut();
				mMap.animateCamera(update);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_marker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraPosition position = mMap.getCameraPosition();
				POI poi = new POI();
				poi.name = "poi name" + count++;
				addMarker(position.target, poi);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().getPoiSearch(MainActivity.this, keyword, new OnResultListener<SearchPOIResult>() {
						
						@Override
						public void onSuccess(SearchPOIResult result) {
							for (int i = 0; i < mAdapter.getCount(); i++) {
								POI poi = mAdapter.getItem(i);
								Marker m = markerResolver.get(poi);
								m.remove();
							}
							mAdapter.clear();
							
							
							for (int i = 0; i < result.searchPoiInfo.pois.poi.size(); i++) {
								final POI poi = result.searchPoiInfo.pois.poi.get(i);
							
								
								mAdapter.add(poi);
								mLoader.loadImage(urls[i % urls.length], new ImageLoadingListener() {
									
									@Override
									public void onLoadingStarted(String imageUri, View view) {
										
									}
									
									@Override
									public void onLoadingFailed(String imageUri, View view,
											FailReason failReason) {
										
									}
									
									@Override
									public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
										poi.bm = loadedImage;
										LatLng target = new LatLng(poi.getLatitude(), poi.getLongitude());
										addMarker(target, poi);
									}
									
									@Override
									public void onLoadingCancelled(String imageUri, View view) {
										
									}
								});
							}
						}
						
						@Override
						public void onFail(int code) {
							
						}
					});
				}
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				POI poi = (POI)listView.getItemAtPosition(position);
				Marker marker = markerResolver.get(poi);
				CameraUpdate update = CameraUpdateFactory.newLatLng(marker.getPosition());
				mMap.animateCamera(update);
				marker.showInfoWindow();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_route);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (startPOI != null && endPOI != null) {
					NetworkManager.getInstnace().getRouteInfo(MainActivity.this, startPOI.getLatitude(), startPOI.getLongitude(), endPOI.getLatitude(), endPOI.getLongitude(), new OnResultListener<CarRouteInfo>() {

						@Override
						public void onSuccess(CarRouteInfo result) {
							PolylineOptions options = new PolylineOptions();
							for (CarFeature feature : result.features) {
								if (feature.geometry.type.equals("LineString")) {
									double[] coord = feature.geometry.coordinates;
									for (int i = 0 ; i < coord.length; i+=2) {										
										options.add(new LatLng(coord[i+1], coord[i]));
									}
								}
							}
							options.color(Color.RED);
							options.width(10);
							mMap.addPolyline(options);
							CarFeature feature = result.features.get(0);
							String message = "total : " + feature.properties.totalDistance + "," + feature.properties.totalTime + "," + feature.properties.totalFare;
							Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onFail(int code) {
							
						}
					});
					startPOI = null;
					endPOI = null;
				} else {
					Toast.makeText(MainActivity.this, "start or end is null", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	int count = 0;
	
	private void addMarker(LatLng target, POI poi) {
		MarkerOptions options = new MarkerOptions();
		options.position(target);
		options.title(poi.name);
		options.snippet(poi.telNo);
		textView.setText(poi.name);
		int measureSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.UNSPECIFIED);
		// View Capture
//		markerText.measure(measureSpec, measureSpec);
//		markerText.layout(0, 0, markerText.getMeasuredWidth(), markerText.getMeasuredHeight());
//		Bitmap bm = getViewBitmap(markerText);
		
//		options.icon(BitmapDescriptorFactory.fromBitmap(poi.bm));
		
		options.icon(BitmapDescriptorFactory.defaultMarker());
		options.anchor(0.5f, 1.0f);
		options.draggable(true);
		Marker marker = mMap.addMarker(options);
		markerResolver.put(poi, marker);
		poiResolver.put(marker, poi);
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
			moveMap(location);
			mLM.removeUpdates(mListener);
		}
	};
	
	private void moveMap(Location location) {
		if (mMap != null) {
			CameraPosition.Builder builder = new CameraPosition.Builder();
			builder.target(new LatLng(location.getLatitude(), location.getLongitude()));
			builder.zoom(15.5f);
//			builder.bearing(20);
//			builder.tilt(30);
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(builder.build());
//			mMap.moveCamera(update);
			mMap.animateCamera(update);
		}
	}
	
	String mProvider = LocationManager.GPS_PROVIDER;
	
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
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment1)).getMap();
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
		mMap.setOnCameraChangeListener(this);
		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
		mMap.setInfoWindowAdapter(new MyInfoWindow(this, poiResolver));
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
	public void onCameraChange(CameraPosition position) {
		LatLng latlng = position.target;
		Log.i("MainActivity","lat : " + latlng.latitude + ", lng : " + latlng.longitude);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		POI poi = poiResolver.get(marker);
		Toast.makeText(this, "marker click : " + poi.name , Toast.LENGTH_SHORT).show();
		marker.showInfoWindow();
		return true;
	}

	POI startPOI, endPOI;
	@Override
	public void onInfoWindowClick(Marker marker) {
		POI poi = poiResolver.get(marker);
		switch(group.getCheckedRadioButtonId()) {
		case R.id.radio_start :
			startPOI = poi;
			Toast.makeText(this, "set start poi", Toast.LENGTH_SHORT).show();
			break;
		case R.id.radio_end :
			endPOI = poi;
			Toast.makeText(this, "set end poi", Toast.LENGTH_SHORT).show();
			break;
		}
		marker.hideInfoWindow();
	}
}
