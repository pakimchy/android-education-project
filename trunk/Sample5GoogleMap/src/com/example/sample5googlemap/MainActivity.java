package com.example.sample5googlemap;

import java.util.ArrayList;
import java.util.HashMap;

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

import com.example.sample5googlemap.NetworkManager.OnResultListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements
		GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener,
		GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {

	GoogleMap mMap;
	ListView listView;
	ArrayAdapter<POI> mAdapter;
	EditText keywordView;
	
	HashMap<POI,Marker> mMarkerResolver = new HashMap<POI,Marker>();
	HashMap<Marker,POI> mPOIResolver = new HashMap<Marker, POI>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<POI>(this, android.R.layout.simple_list_item_1, new ArrayList<POI>());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				POI poi = (POI)listView.getItemAtPosition(position);
				Marker marker = mMarkerResolver.get(poi);
				moveMap(marker.getPosition().latitude, marker.getPosition().longitude);
				marker.showInfoWindow();				
			}
		});
		keywordView = (EditText)findViewById(R.id.keyword);
		setupMapIfNeeded();
		Button btn = (Button) findViewById(R.id.btn_zoom_in);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mMap != null) {
					CameraUpdate update = CameraUpdateFactory.zoomIn();
					mMap.animateCamera(update);
				}
			}
		});

		btn = (Button) findViewById(R.id.btn_zoom_out);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mMap != null) {
					CameraUpdate update = CameraUpdateFactory.zoomOut();
					mMap.animateCamera(update);
				}
			}
		});

		btn = (Button) findViewById(R.id.btn_circle);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mCircle != null) {
					mCircle.setCenter(mMap.getCameraPosition().target);
				} else {
					CircleOptions options = new CircleOptions();
					options.center(mMap.getCameraPosition().target);
					options.radius(100);
					options.fillColor(0x80FF0000);
					options.strokeColor(0x800000FF);
					options.strokeWidth(10);
					mCircle = mMap.addCircle(options);
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstance().getTMapPOI(MainActivity.this, keyword, new OnResultListener<POIResult>() {
						
						@Override
						public void onSuccess(POIResult result) {
							for (POI poi : result.searchPoiInfo.pois.poi) {
								mAdapter.add(poi);
								addMarker(poi);
							}
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});

	}
	
	private void addMarker(POI poi) {
		MarkerOptions options = new MarkerOptions();
		LatLng latLng = new LatLng(poi.getLatitude(), poi.getLongitude());
		options.position(latLng);
		options.anchor(0.5f, 1);
		options.title(poi.name);
		options.snippet(poi.detailAddrName);
		options.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
		options.draggable(false);
		// options.visible(true);
		Marker marker = mMap.addMarker(options);
		mMarkerResolver.put(poi, marker);
		mPOIResolver.put(marker, poi);
	}

	Circle mCircle;

	@Override
	protected void onResume() {
		super.onResume();
		setupMapIfNeeded();
	}

	private void setupMapIfNeeded() {
		if (mMap == null) {
			SupportMapFragment f = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment1);
			mMap = f.getMap();
			if (mMap != null) {
				setupMap();
			}
		}
	}

	private void setupMap() {
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		mMap.setOnMapClickListener(this);
		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
		mMap.setOnMarkerDragListener(this);
		// mMap.setTrafficEnabled(true);
		// mMap.getUiSettings().setZoomControlsEnabled(false);
		// mMap.getUiSettings().setMyLocationButtonEnabled(false);
		// mMap.getUiSettings().setCompassEnabled(true);
		// mMap.getUiSettings().setScrollGesturesEnabled(false);
		moveMap(37.46641962, 126.96064711);
	}

	private void moveMap(double lat, double lng) {
		if (mMap != null) {
			LatLng pos = new LatLng(lat, lng);
			CameraPosition.Builder builder = new CameraPosition.Builder();
			builder.target(pos);
			builder.zoom(15.5f);
			// builder.bearing(30);
			// builder.tilt(30);
			CameraPosition cp = builder.build();

			CameraUpdate update = CameraUpdateFactory.newCameraPosition(cp);

			mMap.moveCamera(update);
			// mMap.animateCamera(update);
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

	@Override
	public void onMapClick(LatLng pos) {
		MarkerOptions options = new MarkerOptions();
		options.position(pos);
		options.anchor(0.5f, 1);
		options.title("marker");
		options.snippet("my marker...");
		options.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
		options.draggable(true);
		// options.visible(true);
		Marker marker = mMap.addMarker(options);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		Toast.makeText(this, "marker : " + marker.getTitle(),
				Toast.LENGTH_SHORT).show();
		marker.showInfoWindow();
		return true;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Toast.makeText(this, "info window click", Toast.LENGTH_SHORT).show();
		marker.hideInfoWindow();
	}

	@Override
	public void onMarkerDrag(Marker arg0) {

	}

	@Override
	public void onMarkerDragEnd(Marker marker) {

	}

	@Override
	public void onMarkerDragStart(Marker arg0) {

	}
}
