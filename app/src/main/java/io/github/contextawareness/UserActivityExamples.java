package io.github.contextawareness;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.UserActivity.UserActivityInfo;
import io.github.contextawareness.core.UserActivity.UserActivityInfoOperators;
import io.github.contextawareness.core.purposes.Purpose;

public class UserActivityExamples {
    private UQI uqi;

    public UserActivityExamples(Context context) {
        this.uqi = new UQI(context);
    }

    // Infer user activity: still
    public void IsStill() {
        try {
            uqi.getData(UserActivityInfo.asUpdates(uqi, DetectedActivity.STILL, 1000), Purpose.UTILITY("Awareness"))
                    .listening("Awareness", UserActivityInfoOperators.isStill())
                    .forEach(new Callback<Item>() {
                        @Override
                        protected void onInput(Item input) {
                            Log.d("Log", input.getAsBoolean("Awareness").toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
