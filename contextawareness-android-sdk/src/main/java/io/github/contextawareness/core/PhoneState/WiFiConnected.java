package io.github.contextawareness.core.PhoneState;


import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Check the WiFi connection status, i.e. WiFiConnected() will return notification when the phone connects WiFi.
 */
public class WiFiConnected extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public WiFiConnected() {
        this.addRequiredPermissions(Manifest.permission.ACCESS_WIFI_STATE);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        int recurrences = 0;
        SignalItem signalItem;

        while (!this.isCancelled) {
            recurrences++;
            WifiManager wifiManager = (WifiManager) uqi.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            boolean isConnected = wifiManager.isWifiEnabled();
            if (isConnected) {
                Log.d(Consts.LIB_TAG, "WiFi connected.");
                this.isContextsAwared = true;
            }
            if (!isConnected) {
                Log.d(Consts.LIB_TAG, "WiFi disconnected.");
                this.isContextsAwared = false;
            }

            signalItem = new SignalItem(System.currentTimeMillis(), this.isContextsAwared);
            if (signalItem != null) this.output(signalItem);

            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences >= numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                this.isCancelled = true;
            } else {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.finish();

    }


}
