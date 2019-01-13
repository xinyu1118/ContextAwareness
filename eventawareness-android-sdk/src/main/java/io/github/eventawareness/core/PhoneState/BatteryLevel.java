package io.github.eventawareness.core.PhoneState;


import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.Operators;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Consts;

/**
 * Listen to the battery level, e.g. BatteryLevel(Operators.LT, 15)
 * will return notification when the battery level is lower than 15%.
 */
public class BatteryLevel extends Contexts {

    private String comparisonOperator;
    private int threshold;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public BatteryLevel(String comparisonOperator, int threshold) {
        this.comparisonOperator = comparisonOperator;
        this.threshold = threshold;
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void provide() {
        int recurrences = 0;
        SignalItem signalItem;

        while (!this.isCancelled) {
            recurrences++;
            BatteryManager batteryManager = (BatteryManager) uqi.getContext().getSystemService(Context.BATTERY_SERVICE);
            int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            switch (comparisonOperator) {
                case Operators.GTE:
                    if (battery >= threshold) {
                        Log.d(Consts.LIB_TAG, "Battery level GTE the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.GT:
                    if (battery > threshold) {
                        Log.d(Consts.LIB_TAG, "Battery level GT the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.LTE:
                    if (battery <= threshold) {
                        Log.d(Consts.LIB_TAG, "Battery level LTE the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.LT:
                    if (battery < threshold) {
                        Log.d(Consts.LIB_TAG, "Battery level LT the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.EQ:
                    if (battery == threshold) {
                        Log.d(Consts.LIB_TAG, "Battery level EQ the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.NEQ:
                    if (battery != threshold) {
                        Log.d(Consts.LIB_TAG, "Battery level NEQ the threshold.");
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
