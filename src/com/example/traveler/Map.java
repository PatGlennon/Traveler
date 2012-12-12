package com.example.traveler;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Map extends MapActivity {
	private static Context mContext;
	public static MapView mapView;
	public static List<Overlay> mapOverlays;
	public static TravelerItemizedOverlay dOverlay;
	public static UserItemizedOverlay userLocationOverlay;
	private static ImageButton centerMap, home;
	public static GeoPoint userLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mContext = this;
        
        //Toast.makeText(mContext, Home.aTP.toString(), Toast.LENGTH_LONG).show();
        
        mapView = (MapView) findViewById(R.id.mapview);
        
        mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_maps_indicator_current_position);
        Drawable pin = this.getResources().getDrawable(R.drawable.pin);
        dOverlay = new TravelerItemizedOverlay(pin, this);
        userLocationOverlay = new UserItemizedOverlay(drawable, this);
        
        //Clear all overlays
        dOverlay.clear();
        mapOverlays.clear();

        //POPULATE WITH SAVED POINTS ON THE MAP!
        for (int i = 0; i < Home.aTP.size(); i++){
            OverlayItem overlayitem = new OverlayItem(new GeoPoint(Home.aTP.get(i).getLatitude(), Home.aTP.get(i).getLongitude()), 
            		Home.aTP.get(i).getCity() + ", " + Home.aTP.get(i).getState(),Home.aTP.get(i).getDate());
            dOverlay.addOverlay(overlayitem);
        }
        
        mapOverlays.add(dOverlay);
        
        centerMap = (ImageButton) findViewById(R.id.center);
        home = (ImageButton) findViewById(R.id.home);
        
        //On centerMap click, center the map on the user's location
        centerMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mapView.getController().animateTo(userLocation);
			}
		});
        //Home Button
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
              userLocationOverlay.clear();
              userLocation = new GeoPoint((int) (location.getLatitude()*1E6), (int) (location.getLongitude()*1E6));
              
              OverlayItem overlayitem = new OverlayItem(userLocation, "This is you","...you are here.");
              userLocationOverlay.addOverlay(overlayitem);
              mapOverlays.add(userLocationOverlay);
            }
            
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
          };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
    
    public void onResume(){
    	super.onResume();
    	//POPULATE WITH SAVED POINTS ON THE MAP!
    	dOverlay.clear();
    	mapOverlays.clear();
        for (int i = 0; i < Home.aTP.size(); i++){
            OverlayItem overlayitem = new OverlayItem(new GeoPoint(Home.aTP.get(i).getLatitude(), Home.aTP.get(i).getLongitude()), 
            		Home.aTP.get(i).getCity() + ", " + Home.aTP.get(i).getState(),Home.aTP.get(i).getDate());
            dOverlay.addOverlay(overlayitem);
        }
        mapOverlays.add(dOverlay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.add_place:
            addPlace();
            return true;
        case R.id.menu_settings:
        	goToMenu();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void goToMenu(){
    	Intent intent = new Intent(mContext, Help.class);
  		startActivity(intent);	
  		finish();
    }
    
    public void addPlace(){
    	int lat = userLocation.getLatitudeE6();
    	int lng = userLocation.getLongitudeE6();
    	Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
    	List<Address> addresses = null;
        try{
        	addresses = geocoder.getFromLocation((double) (lat/1E6),(double) (lng/1E6),1);
        }catch(Exception e){
        	e.printStackTrace();
        }
    	
    	String city = addresses.get(0).getLocality();
    	String state = addresses.get(0).getAdminArea();
    	
    	Intent intent = new Intent(mContext, AddPlace.class);
    	intent.putExtra("lat", lat);
    	intent.putExtra("lng", lng);
    	intent.putExtra("city", city);
    	intent.putExtra("state", state);
  		startActivity(intent);
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
