package com.builders.farva.utils;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import de.greenrobot.event.EventBus;

/**
 * Created by Shabaz on 7/26/2015.
 */
public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    final String tag = LocationManager.class.getSimpleName();
    public GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    LocationRequest mLocationRequest;
    static LocationManager mLocationManager;
    private LocationManager()
    {
        buildGoogleApiClient();
        createLocationRequest();
    }
    public static LocationManager  getLocationManagerInstance()
    {
        if (mLocationManager==null)
            mLocationManager = new LocationManager();
        return mLocationManager;

    }
    public  Location getLocation()
    {
        return mCurrentLocation;
    }

    protected synchronized void buildGoogleApiClient() {
        Log.d(tag, "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(Farva.staticContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.
                requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation  = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation=location;
        EventBus.getDefault().post(location);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(Farva.staticContext,"onConnectionFailed",Toast.LENGTH_SHORT).show();

    }
}
