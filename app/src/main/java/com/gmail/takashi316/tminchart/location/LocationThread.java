package com.gmail.takashi316.tminchart.location;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;

/**
 * Created by sasaki on 2014/08/08.
 */
public class LocationThread extends Thread implements LocationListener {
    public double longitude;
    public double latitude;
    public String address = null;
    private LocationManager locationManager;
    private String locationProvider;
    private Geocoder geocoder;
    private Runnable callback;

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public LocationThread(final Context context) {
        super("LocationThread");
        this.locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(false);
        this.locationProvider = this.locationManager.getBestProvider(criteria, true);
        this.geocoder = new Geocoder(context);
    }

    @Override
    public void run() {
        try {
            this.locationManager.requestLocationUpdates(locationProvider, 0, 0, LocationThread.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//run

    @Override
    public void onLocationChanged(Location location) {
        this.locationManager.removeUpdates(this);
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        try {
            final List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() >= 1) {
                final Address address = addresses.get(0);
                this.address = "";
                for (int i = 0; i < address.getMaxAddressLineIndex(); ++i) {
                    this.address += address.getAddressLine(i);
                }//for
            }//if
        } catch (IOException e) {
            e.printStackTrace();
        }//try
        if (this.callback != null) {
            this.callback.run();
        }
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

    public void stopRequestLocationUpdates() {
        this.locationManager.removeUpdates(this);
    }

    @Override
    protected void finalize() throws Throwable {
        this.locationManager.removeUpdates(this);
        super.finalize();
    }
}
