package io.github.contextawareness.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.utils.Consts;

/**
 * Recognize user activities.
 */
public class UserActivityRecognition extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;

    public static long interval; // should be passed to BackgroundDetectedActivitiesService.java
    private int queryActivity;
    int recurrences;

    UserActivityRecognition(int queryActivity) {
        recurrences = 0;
        this.queryActivity = queryActivity;
        this.pStreamProvider = this.getProvider();
    }


    @Override
    public PStreamProvider getProvider() {
        return UserActivityInfo.asUpdates(1000);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("activity"))
                .setField("context_signal", UserActivityInfoOperators.recognition(queryActivity));
        return null;
    }

}
