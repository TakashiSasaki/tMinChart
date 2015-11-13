package com.gmail.takashi316.tminchart.location;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by sasaki on 2014/08/08.
 */

class LocationResults {
    public double longitude;
    public double latitude;
    public String address = null;
}

public class Location implements LocationListener {
    public LocationResults locationResults = new LocationResults();
    private LocationManager locationManager;
    private String locationProvider;
    private Geocoder geocoder;
    private Runnable callback;

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public Location(final Context context) {
        this.locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(false);
        this.locationProvider = this.locationManager.getBestProvider(criteria, true);
        this.geocoder = new Geocoder(context);
        this.locationManager.requestLocationUpdates(locationProvider, 0, 0, Location.this);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        this.locationManager.removeUpdates(this);
        this.locationResults.longitude = location.getLongitude();
        this.locationResults.latitude = location.getLatitude();
        try {
            final List<Address> addresses = geocoder.getFromLocation(locationResults.latitude, locationResults.longitude, 1);
            if (addresses.size() >= 1) {
                final Address address = addresses.get(0);
                this.locationResults.address = "";
                for (int i = 0; i <= address.getMaxAddressLineIndex(); ++i) {
                    this.locationResults.address += address.getAddressLine(i);
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

    @Override
    protected void finalize() throws Throwable {
        if (this.locationManager != null) {
            this.locationManager.removeUpdates(this);
        }
        super.finalize();
    }

    public String getJson() {
        ObjectMapper object_mapper = new ObjectMapper();
        try {
            String json_string = object_mapper.writeValueAsString(this.locationResults);
            return json_string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAddress() {
        return locationResults.address;
    }

    public double getLongitude() {
        return locationResults.longitude;
    }

    public double getLatitude() {
        return locationResults.latitude;
    }
}
