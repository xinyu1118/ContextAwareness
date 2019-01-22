package io.github.contextawareness.core.PhoneState;


import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Listen to the screen state, i.e. 'ScreenOn()' will notify apps when the screen is on.
 */
public class ScreenOn extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public ScreenOn() {
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

            PowerManager powerManager = (PowerManager) uqi.getContext().getSystemService(Context.POWER_SERVICE);
            boolean ifOpen = false;
            try {
                assert powerManager != null;
                ifOpen = powerManager.isScreenOn();
            } catch (NullPointerException e) {
                Log.d(Consts.LIB_TAG, "PowerManager throws NullPointerException.");
                e.printStackTrace();
            }

            if (ifOpen) {
                Log.d(Consts.LIB_TAG, "Screen on.");
                this.isContextsAwared = true;
            }
            if (!ifOpen) {
                Log.d(Consts.LIB_TAG, "Screen off.");
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
