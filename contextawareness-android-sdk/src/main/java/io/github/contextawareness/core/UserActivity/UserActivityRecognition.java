package io.github.contextawareness.core.UserActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Recognize user activities.
 */
public class UserActivityRecognition extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    public static long interval; // should be passed to BackgroundDetectedActivitiesService.java
    private int queryActivity;
    int recurrences;

    BroadcastReceiver broadcastReceiver;
    Intent intent;

    public UserActivityRecognition(int queryActivity) {
        recurrences = 0;
        this.queryActivity = queryActivity;
        this.addRequiredPermissions("com.google.android.gms.permission.ACTIVITY_RECOGNITION");
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    private void listening() {
        intent = new Intent(uqi.getContext(), BackgroundDetectedActivitiesService.class);
        uqi.getContext().startService(intent);
    }

    @Override
    protected void provide() {
        listening();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("activity_intent")) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                    handleUserActivity(type, confidence);
                }
            }
        };
        LocalBroadcastManager.getInstance(uqi.getContext()).registerReceiver(broadcastReceiver,
                new IntentFilter("activity_intent"));
    }


    private void handleUserActivity(int type, int confidence) {

        switch (type) {
            case DetectedActivity.IN_VEHICLE:
                if (queryActivity == DetectedActivity.IN_VEHICLE) {
                    Log.d(Consts.LIB_TAG, "IN_VEHICLE" + ", Confidence: " + confidence);
                    recurrences++;
                    this.isContextsAwared = true;
                    if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                        Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                        this.isCancelled = true;
                        LocalBroadcastManager.getInstance(uqi.getContext()).unregisterReceiver(broadcastReceiver);
                        uqi.getContext().stopService(intent);
                    } else {
                        this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
                        this.isContextsAwared = false;
                    }
                }
                break;
            case DetectedActivity.ON_BICYCLE:
                if (queryActivity == DetectedActivity.ON_BICYCLE) {
                    Log.d(Consts.LIB_TAG, "ON_BICYCLE"+", Confidence: "+confidence);
                    recurrences++;
                    this.isContextsAwared = true;
                    if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                        Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                        this.isCancelled = true;
                        LocalBroadcastManager.getInstance(uqi.getContext()).unregisterReceiver(broadcastReceiver);
                        uqi.getContext().stopService(intent);
                    } else {
                        this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
                        this.isContextsAwared = false;
                    }
                }
                break;
            case DetectedActivity.ON_FOOT:
                if (queryActivity == DetectedActivity.ON_FOOT) {
                    Log.d(Consts.LIB_TAG, "ON_FOOT"+", Confidence: "+confidence);
                    recurrences++;
                    this.isContextsAwared = true;
                    if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                        Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                        this.isCancelled = true;
                        LocalBroadcastManager.getInstance(uqi.getContext()).unregisterReceiver(broadcastReceiver);
                        uqi.getContext().stopService(intent);
                    } else {
                        this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
                        this.isContextsAwared = false;
                    }
                }
                break;
            case DetectedActivity.RUNNING:
                if (queryActivity == DetectedActivity.RUNNING) {
                    Log.d(Consts.LIB_TAG, "RUNNING"+", Confidence: "+confidence);
                    recurrences++;
                    this.isContextsAwared = true;
                    if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                        Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                        this.isCancelled = true;
                        LocalBroadcastManager.getInstance(uqi.getContext()).unregisterReceiver(broadcastReceiver);
                        uqi.getContext().stopService(intent);
                    } else {
                        this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
                        this.isContextsAwared = false;
                    }
                }
                break;
            case DetectedActivity.STILL:
                if (queryActivity == DetectedActivity.STILL) {
                    Log.d(Consts.LIB_TAG, "STILL"+", Confidence: "+confidence);
                    recurrences++;
                    this.isContextsAwared = true;
                    if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                        Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                        this.isCancelled = true;
                        LocalBroadcastManager.getInstance(uqi.getContext()).unregisterReceiver(broadcastReceiver);
                        uqi.getContext().stopService(intent);
                    } else {
                        this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
                        this.isContextsAwared = false;
                    }
                }
                break;
            case DetectedActivity.TILTING:
                if (queryActivity == DetectedActivity.TILTING) {
                    Log.d(Consts.LIB_TAG, "TILTING"+", Confidence: "+confidence);
                    recurrences++;
                    this.isContextsAwared = true;
                    if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                        Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                        this.isCancelled = true;
                        LocalBroadcastManager.getInstance(uqi.getContext()).unregisterReceiver(broadcastReceiver);
                        uqi.getContext().stopService(intent);
                    } else {
                        this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
                        this.isContextsAwared = false;
                    }
                }
                break;
            case DetectedActivity.WALKING:
                if (queryActivity == DetectedActivity.WALKING) {
                    Log.d(Consts.LIB_TAG, "WALKING"+", Confidence: "+confidence);
                    recurrences++;
                    this.isContextsAwared = true;
                    if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                        Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                        this.isCancelled = true;
                        LocalBroadcastManager.getInstance(uqi.getContext()).unregisterReceiver(broadcastReceiver);
                        uqi.getContext().stopService(intent);
                    } else {
                        this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));
                        this.isContextsAwared = false;
                    }
                }
                break;
            case DetectedActivity.UNKNOWN:
                Log.d(Consts.LIB_TAG, "UNKNOWN"+", Confidence: "+confidence);
                break;
            default:
                Log.d(Consts.LIB_TAG, "No matchable Activity, please select one from the predefined collection.");
        }

    }

}
