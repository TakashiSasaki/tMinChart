package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by sasaki on 2014/08/08.
 */
public class LocationThread extends HandlerThread implements LocationListener {
    LocationManager locationManager;
    String locationProvider;
    double longitude;
    double latitude;
    String address = null;
    boolean locationChanged = false;
    Geocoder geocoder;
    EditText editTextLongitude, editTextLatitude, editTextAddress;
    Context context;

    public LocationThread(final Context context, EditText edit_text_address){
        super("LocationThread");
        locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();
        this.context = context;
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(false);
        locationProvider = this.locationManager.getBestProvider(criteria, true);
        geocoder = new Geocoder(context);
    }

    @Override
    public void run() {
        super.run();
        try {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, LocationThread.this);
            super.run();
        } catch (Exception e){
            Toast.makeText (this.context, "failed to get location.", Toast.LENGTH_LONG).show();
        }//try
    }//run

    @Override
    public void onLocationChanged(Location location) {
        this.locationChanged = true;
        this.locationManager.removeUpdates(this);
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        try {
            final List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses.size()>=1){
                final Address address = addresses.get(0);
                this.address = "";
                for(int i=0; i<address.getMaxAddressLineIndex(); ++i) {
                    this.address += address.getAddressLine(i);
                }//for
                editTextAddress.setText(LocationThread.this.address);
            }//if
        } catch (IOException e){
        }//try
    }//onLocationChanged

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
