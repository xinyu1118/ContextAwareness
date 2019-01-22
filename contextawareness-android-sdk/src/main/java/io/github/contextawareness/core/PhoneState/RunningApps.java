package io.github.contextawareness.core.PhoneState;


import android.app.ActivityManager;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.Operators;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Check the number of running apps, e.g. RunningApps(Operators.GTE, 10)
 * will return notification when the number of running apps is gte 10.
 */
public class RunningApps extends Contexts {

    private String comparisonOperator;
    private int threshold;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public RunningApps(String comparisonOperator, int threshold) {
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
            ActivityManager activityManager = (ActivityManager) uqi.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            assert activityManager != null;
            final List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
            switch (comparisonOperator) {
                case Operators.GTE:
                    if (appTasks.size() >= threshold) {
                        Log.d(Consts.LIB_TAG, "Running apps GTE the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.GT:
                    if (appTasks.size() > threshold) {
                        Log.d(Consts.LIB_TAG, "Running apps GT the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.LTE:
                    if (appTasks.size() <= threshold) {
                        Log.d(Consts.LIB_TAG, "Running apps LTE the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.LT:
                    if (appTasks.size() < threshold) {
                        Log.d(Consts.LIB_TAG, "Running apps LT the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.EQ:
                    if (appTasks.size() == threshold) {
                        Log.d(Consts.LIB_TAG, "Running apps EQ the threshold.");
                        this.isContextsAwared = true;
                    }
                    break;
                case Operators.NEQ:
                    if (appTasks.size() != threshold) {
                        Log.d(Consts.LIB_TAG, "Running apps NEQ the threshold.");
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
