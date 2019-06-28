package io.github.contextawareness.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.utils.Consts;

/**
 * Provide motion context with Google Awareness API
 */
class UserActivityInfoProvider extends PStreamProvider {
    private long interval;
    BroadcastReceiver broadcastReceiver;
    Intent intent;

    public UserActivityInfoProvider(long interval) {
        this.addRequiredPermissions("com.google.android.gms.permission.ACTIVITY_RECOGNITION");
        this.interval = interval;
    }

    @Override
    protected void provide() {
        intent = new Intent(this.getContext(), BackgroundDetectedActivitiesService.class);
        this.getContext().startService(intent);

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
        String motionType = null;
        switch (type) {
            case DetectedActivity.IN_VEHICLE:
                Log.d(Consts.LIB_TAG, "IN_VEHICLE" + ", Confidence: " + confidence);
                motionType = "in_vehicle";
                break;
            case DetectedActivity.ON_BICYCLE:
                Log.d(Consts.LIB_TAG, "ON_BICYCLE"+", Confidence: "+confidence);
                motionType = "on_bicycle";
                break;
            case DetectedActivity.ON_FOOT:
                Log.d(Consts.LIB_TAG, "ON_FOOT"+", Confidence: "+confidence);
                motionType = "on_foot";
                break;
            case DetectedActivity.RUNNING:
                Log.d(Consts.LIB_TAG, "RUNNING"+", Confidence: "+confidence);
                motionType = "running";
                break;
            case DetectedActivity.STILL:
                Log.d(Consts.LIB_TAG, "STILL"+", Confidence: "+confidence);
                motionType = "still";
                break;
            case DetectedActivity.TILTING:
                Log.d(Consts.LIB_TAG, "TILTING"+", Confidence: "+confidence);
                motionType = "tilting";
                break;
            case DetectedActivity.WALKING:
                Log.d(Consts.LIB_TAG, "WALKING"+", Confidence: "+confidence);
                motionType = "walking";
                break;
            case DetectedActivity.UNKNOWN:
                Log.d(Consts.LIB_TAG, "UNKNOWN"+", Confidence: "+confidence);
                motionType = "unknown";
                break;
            default:
                Log.d(Consts.LIB_TAG, "No matchable activity, please select one from the predefined collection.");
        }

        while (!this.isCancelled) {
            UserActivityInfo activityItem = new UserActivityInfo(System.currentTimeMillis(), motionType);

            if (activityItem != null) this.output(activityItem);
            try {
                Thread.sleep(this.interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.finish();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(broadcastReceiver);
        this.getContext().stopService(intent);
    }

}
