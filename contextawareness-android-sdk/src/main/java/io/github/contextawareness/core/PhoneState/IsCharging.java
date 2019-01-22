package io.github.contextawareness.core.PhoneState;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Listen to the charging status, e.g. IsCharging() will return notification
 * when the phone is charging, IsCharging(IsCharging.USB_Mode) will notify apps
 * when the phone is charging with USB.
 */
public class IsCharging extends Contexts {

    int chargingMode = 0;
    public static final int USB_Mode = 1;
    public static final int AC_Mode = 2;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public IsCharging() {
    }

    public IsCharging(int chargingMode) {
        this.chargingMode = chargingMode;
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
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = uqi.getContext().registerReceiver(null, intentFilter);
            assert batteryStatus != null;
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            //boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
            boolean usbCharge = status == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = status == BatteryManager.BATTERY_PLUGGED_AC;

            switch (chargingMode) {
                case USB_Mode:
                    if (usbCharge) {
                        Log.d(Consts.LIB_TAG, "Charging with USB.");
                        this.isContextsAwared = true;
                    }
                    break;
                case AC_Mode:
                    if (acCharge) {
                        Log.d(Consts.LIB_TAG, "Charging with AC current.");
                        this.isContextsAwared = true;
                    }
                    break;
                case 0:
                    if (usbCharge || acCharge) {
                        Log.d(Consts.LIB_TAG, "Charging.");
                        this.isContextsAwared = true;
                    }
                    break;
                default:
                    Log.d(Consts.LIB_TAG, "No matchable operator, please select one from the predefined collection.");
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
            this.isContextsAwared = false;
        }
        this.finish();
    }
}
