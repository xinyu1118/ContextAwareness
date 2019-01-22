package io.github.contextawareness.core.EnvironmentVariable;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.location.LatLon;
import io.github.contextawareness.utils.Consts;
import io.github.contextawareness.utils.LocationUtils;
import io.github.contextawareness.utils.Logging;

/**
 * Monitor location in an area with Google service,
 * the area could be specified by latitude, longitude and radius,
 * or minimum latitude and longitude, maximum latitude and longitude,
 * or a place name and the maximum radius.
 */
public class GoogleLocationInArea extends Contexts implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    private double centerLat;
    private double centerLng;
    private double radius;

    private double minLat;
    private double minLng;
    private double maxLat;
    private double maxLng;

    private String level = Geolocation.LEVEL_EXACT;
    int recurrences;

    int constructor;
    private Location lastLocation = null;

    public GoogleLocationInArea(double centerLat, double centerLng, double radius) {
        recurrences = 0;
        constructor = 0;
        this.centerLat = centerLat;
        this.centerLng = centerLng;
        this.radius = radius;
        this.addRequiredPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        this.addRequiredPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public GoogleLocationInArea(String placeName, double radius) {
        recurrences = 0;
        constructor = 0;
        Geocoder geocoder = new Geocoder(this.getContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(placeName, 5);
            if (addresses == null) return; // crash
            Address location = addresses.get(0);
            this.centerLat = location.getLatitude();
            this.centerLat = location.getLongitude();
            this.radius = radius;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.addRequiredPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        this.addRequiredPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public GoogleLocationInArea(double minLat, double minLng, double maxLat, double maxLng) {
        recurrences = 0;
        constructor = 1;
        this.minLat = minLat;
        this.minLng = minLng;
        this.maxLat = maxLat;
        this.maxLng = maxLng;
        this.addRequiredPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        this.addRequiredPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    private transient GoogleApiClient mGoogleApiClient = null;

    @Override
    protected void provide() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
            Logging.debug(Consts.LIB_TAG + "Connection lost. Cause: Network Lost.");
        } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
            Logging.debug(Consts.LIB_TAG + "Connection lost. Cause: Service Disconnected");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Logging.warn(Consts.LIB_TAG + "Not connected with GoogleApiClient");
        this.finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) return;
        // Used to calculate speed, otherwise 0 will be always returned.
        if (lastLocation != null) {
            double elapsedTime = (location.getTime() - lastLocation.getTime()) / 1_000;
            Float fSpeed = new Double(lastLocation.distanceTo(location) / elapsedTime).floatValue();
            location.setSpeed(fSpeed);
        }
        lastLocation = location;

        if (constructor == 0) {
            LatLon latLon = new LatLon(location.getLatitude(), location.getLongitude());
            LatLon centerLatLon = new LatLon(centerLat, centerLng);
            Double distance = LocationUtils.getDistanceBetween(centerLatLon, latLon);
            if (distance <= radius) {
                Log.d(Consts.LIB_TAG, "Location in Area.");
                this.isContextsAwared = true;
            }
        } else {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            if (minLat <= lat && lat <= maxLat && minLng <= lng && lng <= maxLng) {
                Log.d(Consts.LIB_TAG, "Location in Area.");
                this.isContextsAwared = true;
            }
        }

        recurrences++;
        if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
            Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
            this.isCancelled = true;
            this.stopLocationUpdate();
        } else {
            this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
            this.isContextsAwared = false;
        }
    }

    private void startLocationUpdate() {
        long fastInterval = interval / 2;

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(fastInterval);

        if (Geolocation.LEVEL_EXACT.equals(this.level))
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        else if (Geolocation.LEVEL_BUILDING.equals(this.level))
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        else
            mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        this.finish();
    }
}
