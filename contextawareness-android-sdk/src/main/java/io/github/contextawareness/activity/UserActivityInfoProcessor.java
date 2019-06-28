package io.github.contextawareness.activity;


import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Get device id
 */
class UserActivityInfoProcessor extends Function<Item, Boolean> {
    private int queryActivity;

    UserActivityInfoProcessor(int queryActivity) {
        this.queryActivity = queryActivity;
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        String motionType = input.getValueByField("motion_type");
        int motionTypeValue = 10;
        Boolean result = false;

        switch (motionType) {
            case "in_vehicle":
                motionTypeValue = Activity.IN_VEHICLE;
                break;
            case "on_bicycle":
                motionTypeValue = Activity.ON_BICYCLE;
                break;
            case "on_foot":
                motionTypeValue = Activity.ON_FOOT;
                break;
            case "running":
                motionTypeValue = Activity.RUNNING;
                break;
            case "still":
                motionTypeValue = Activity.STILL;
                break;
            case "tilting":
                motionTypeValue = Activity.TILTING;
                break;
            case "walking":
                motionTypeValue = Activity.WALKING;
                break;
            case "unknown":
                motionTypeValue = Activity.UNKNOWN;
                break;
            default:
                Log.d(Consts.LIB_TAG, "No matchable Activity, please select one from the predefined collection.");
        }

        if (motionTypeValue == queryActivity)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
}