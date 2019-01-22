package io.github.contextawareness.core.EnvironmentVariable;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;

import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.location.LatLon;
import io.github.contextawareness.utils.Consts;
import io.github.contextawareness.utils.LocationUtils;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Monitor location in an area,
 * the area could be specified by latitude, longitude and radius,
 * or minimum latitude and longitude, maximum latitude and longitude,
 * or a place name and the maximum radius.
 */
public class LocationInArea extends Contexts {

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

    private static Object monitor = new Object();
    private static boolean isCharged = false;
    private boolean broadcastRegistered = false;
    BroadcastReceiver receiver;

    private String level = Geolocation.LEVEL_EXACT;
    int recurrences;

    int constructor;

    public LocationInArea(double centerLat, double centerLng, double radius) {
        recurrences = 0;
        constructor = 0;
        this.centerLat = centerLat;
        this.centerLng = centerLng;
        this.radius = radius;
        this.addRequiredPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        this.addRequiredPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public LocationInArea(String placeName, double radius) {
        recurrences = 0;
        constructor = 0;
        Geocoder geocoder = new Geocoder(this.getContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(placeName, 5);
            if (addresses == null) return;
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

    public LocationInArea(double minLat, double minLng, double maxLat, double maxLng) {
        recurrences = 0;
        constructor = 1;
        this.minLat = minLat;
        this.minLng = minLng;
        this.maxLat = maxLat;
        this.maxLng = maxLng;
        this.addRequiredPermissions(Manifest.permission.ACCESS_FINE_LOCATION);
        this.addRequiredPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;

        // If the current battery level is below 15%, stop context awareness until
        // the device gets charged.
        BatteryManager bm = (BatteryManager)uqi.getContext().getSystemService(BATTERY_SERVICE);
        int batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if (batteryLevel <= 15) {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = uqi.getContext().registerReceiver(null, intentFilter);
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            // if the device is charging, just sample data immediately, otherwise
            // sleep until it is charged.
            if (!isCharging) {
                Log.d(Consts.LIB_TAG, "Contexts will be paused sensing until being charged.");
                new WaitThread().start();
                new NotifyThread().start();
            }
        }

    }

    private transient LocationManager locationManager;
    private transient LocationListener locationListener;

    @Override
    protected void provide() {
        Looper.prepare();
        locationManager = (LocationManager) uqi.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        long minTime = interval;
        float minDistance = 0;
        String provider;
        if (Geolocation.LEVEL_EXACT.equals(level)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        else {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        locationManager.requestLocationUpdates(provider, minTime, minDistance, locationListener);
        Looper.loop();
    }

    private final class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location == null) return;

            if (constructor == 0) {
                LatLon latLon = new LatLon(location.getLatitude(), location.getLongitude());
                LatLon centerLatLon = new LatLon(centerLat, centerLng);
                Double distance = LocationUtils.getDistanceBetween(centerLatLon, latLon);
                if (distance <= radius) {
                    Log.d(Consts.LIB_TAG, "Location in Area.");
                    LocationInArea.this.isContextsAwared = true;
                }
            } else {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                if (minLat <= lat && lat <= maxLat && minLng <= lng && lng <= maxLng) {
                    Log.d(Consts.LIB_TAG, "Location in Area.");
                    LocationInArea.this.isContextsAwared = true;
                }
            }

            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                LocationInArea.this.isCancelled = true;
                this.stopLocationUpdate();
            } else {
                LocationInArea.this.output(new SignalItem(System.currentTimeMillis(), LocationInArea.this.isContextsAwared));
                LocationInArea.this.isContextsAwared = false;
            }

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
            this.stopLocationUpdate();
        }

        private void stopLocationUpdate() {
            if (locationManager != null)
                locationManager.removeUpdates(locationListener);
            LocationInArea.this.finish();
        }

    }

    // If the device is not charged, wait the thread until power is connected.
    public class WaitThread extends Thread {
        public void run() {
            while(!isCharged) {
                synchronized(monitor) {
                    try {
                        monitor.wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Notify the thread to keep on executing subsequent codes if charged.
    public class NotifyThread extends Thread {
        public void run() {
            IntentFilter ifilter = new IntentFilter();
            ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
            //ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                        isCharged = true;
                        synchronized (monitor) {
                            monitor.notifyAll();
                        }
                    }
                }
            };
            uqi.getContext().registerReceiver(receiver, ifilter);
            broadcastRegistered = true;
        }
    }

}
