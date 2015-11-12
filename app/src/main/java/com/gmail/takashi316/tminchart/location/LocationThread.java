package com.gmail.takashi316.tminchart.location;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * Created by sasaki on 2014/08/08.
 */
public class LocationThread extends HandlerThread implements LocationListener {
    private LocationManager locationManager;
    private String locationProvider;
    private double longitude;
    private double latitude;
    private String address = null;
    private boolean locationChanged = false;
    private Geocoder geocoder;
    private int intervalMillis;
    private int count;

    public void setEditTextLongitude(EditText editTextLongitude) {
        this.editTextLongitude = editTextLongitude;
    }

    public void setEditTextLatitude(EditText editTextLatitude) {
        this.editTextLatitude = editTextLatitude;
    }

    public void setEditTextAddress(EditText editTextAddress) {
        this.editTextAddress = editTextAddress;
    }

    public void setEditTextCount(EditText editTextCount) {
        this.editTextCount = editTextCount;
    }

    private EditText editTextLongitude;
    private EditText editTextLatitude;
    private EditText editTextAddress;
    private EditText editTextCount;
    private Context context;

    public LocationThread(final Context context, int interval_millis, int count) {
        super("LocationThread");
        this.intervalMillis = interval_millis;
        this.count = count;
        this.locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();
        this.context = context;
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(false);
        this.locationProvider = this.locationManager.getBestProvider(criteria, true);
        this.geocoder = new Geocoder(context);
    }

    @Override
    public void run() {
        for (; count > 0; --count) {
            if (this.editTextCount != null) {
                this.editTextCount.setText(Integer.toString(this.count));
            }
            try {
                this.locationManager.requestLocationUpdates(locationProvider, 0, 0, LocationThread.this);
            } catch (Exception e) {
            }//try
            try {
                this.sleep(this.intervalMillis);
            } catch (InterruptedException e) {
            }//try
        }
        this.locationManager.removeUpdates(LocationThread.this);
    }//run

    @Override
    public void onLocationChanged(Location location) {
        this.locationChanged = true;
        this.locationManager.removeUpdates(this);
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        if (this.editTextLatitude != null) {
            this.editTextLatitude.setText(Double.toString(this.latitude));
        }//if
        if (this.editTextLongitude != null) {
            this.editTextLongitude.setText(Double.toString(this.longitude));
        }//if
        try {
            final List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() >= 1) {
                final Address address = addresses.get(0);
                this.address = "";
                for (int i = 0; i < address.getMaxAddressLineIndex(); ++i) {
                    this.address += address.getAddressLine(i);
                }//for
                if (this.editTextAddress != null) {
                    editTextAddress.setText(LocationThread.this.address);
                }//if
            }//if
        } catch (IOException e) {
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

    public void stopRequestLocationUpdates() {
        this.count = 0;
    }
}
