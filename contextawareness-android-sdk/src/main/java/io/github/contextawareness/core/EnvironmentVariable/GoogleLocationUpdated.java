package io.github.contextawareness.core.EnvironmentVariable;

import android.Manifest;
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

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.utils.Consts;
import io.github.contextawareness.utils.Logging;

/**
 * Monitor location updates with Google service.
 */
public class GoogleLocationUpdated extends Contexts implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    private String level = Geolocation.LEVEL_EXACT;
    int recurrences;

    private Location lastLocation = null;

    public GoogleLocationUpdated() {
        recurrences = 0;
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

        this.isContextsAwared = true;
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