package io.github.contextawareness.core.UserActivity;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.FenceState;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import io.github.contextawareness.core.BuildConfig;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.utils.Consts;
import java.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Provide motion context with Google Awareness API
 */
class UserActivityInfoProvider extends PStreamProvider {

    private UQI uqi;
    private int numOfRecurrences;
    public static long interval; // should be passed to BackgroundDetectedActivitiesService.java
    private int queryActivity;
    int recurrences;

    BroadcastReceiver broadcastReceiver;
    Intent intent;


    public UserActivityInfoProvider(UQI uqi, int queryActivity, long interval) {
        recurrences = 0;
        this.queryActivity = queryActivity;
        this.addRequiredPermissions("com.google.android.gms.permission.ACTIVITY_RECOGNITION");

        this.numOfRecurrences = 0;
        this.uqi = uqi;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        intent = new Intent(uqi.getContext(), BackgroundDetectedActivitiesService.class);
        uqi.getContext().startService(intent);

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
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver,
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
