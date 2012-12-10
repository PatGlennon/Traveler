package com.example.traveler;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Map extends MapActivity {
	private static Context mContext;
	private static MapView mapView;
	private static List<Overlay> mapOverlays;
	private static TravelerItemizedOverlay dOverlay;
	private static ImageButton centerMap, home;
	public static GeoPoint userLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mContext = this;
        
        mapView = (MapView) findViewById(R.id.mapview);
        
        mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_maps_indicator_current_position);
        dOverlay = new TravelerItemizedOverlay(drawable, this);
        
        centerMap = (ImageButton) findViewById(R.id.center);
        home = (ImageButton) findViewById(R.id.home);
        
        /*  Doesn't do anything :(
        //If the button has focus, set background to yellow, else set to grey.
        centerMap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus){
					Toast.makeText(mContext, "onfocus", Toast.LENGTH_LONG).show();
					centerMap.setBackgroundColor(-256);
				}else{
					centerMap.setBackgroundColor(-23472348);
				}
			}
		});*/
        
        //On centerMap click, center the map on the user's location
        centerMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mapView.getController().animateTo(userLocation);
			}
		});
        home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
        
        
        
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
              mapOverlays.clear();
              userLocation = new GeoPoint((int) (location.getLatitude()*1E6), (int) (location.getLongitude()*1E6));
              
              OverlayItem overlayitem = new OverlayItem(userLocation, "This is you","...you are here.");
              dOverlay.addOverlay(overlayitem);
              mapOverlays.add(dOverlay);
              
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
          };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
