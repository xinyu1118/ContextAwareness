package io.github.contextawareness.core.EnvironmentVariable;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.utils.Consts;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Monitor location updates.
 */
public class LocationUpdated extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    private static Object monitor = new Object();
    private static boolean isCharged = false;
    private boolean broadcastRegistered = false;
    BroadcastReceiver receiver;

    private String level = Geolocation.LEVEL_EXACT;
    int recurrences;

    public LocationUpdated() {
        recurrences = 0;
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
            LocationUpdated.this.isContextsAwared = true;
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                LocationUpdated.this.isCancelled = true;
                this.stopLocationUpdate();
            } else {
                LocationUpdated.this.output(new SignalItem(System.currentTimeMillis(), LocationUpdated.this.isContextsAwared));
                LocationUpdated.this.isContextsAwared = false;
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
            LocationUpdated.this.finish();
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
