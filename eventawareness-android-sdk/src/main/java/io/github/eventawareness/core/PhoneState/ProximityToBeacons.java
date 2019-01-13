package io.github.eventawareness.core.PhoneState;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Consts;

/**
 * Listen to the phone proximity to beacons, i.e. ProximityToBeacons()
 * will return notification when the phone is proximity to a beacon area.
 */
public class ProximityToBeacons extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public ProximityToBeacons() {
        this.addRequiredPermissions(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        SignalItem signalItem = null;
        int recurrences = 0;

        while (!this.isCancelled) {
            recurrences++;
            Intent intent = new Intent(uqi.getContext(), BeaconMonitoringActivity.class);
            this.getUQI().getContext().startActivity(intent);

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
