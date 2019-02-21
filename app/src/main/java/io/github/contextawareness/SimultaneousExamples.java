package io.github.contextawareness;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import java.util.HashMap;
import java.util.LinkedList;

import io.github.contextawareness.audio.Audio;
import io.github.contextawareness.audio.AudioOperators;
import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.ProviderAnd_Or.Environment;
import io.github.contextawareness.core.ProviderAnd_Or.EnvironmentFactor;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.UserActivity.UserActivityInfo;
import io.github.contextawareness.core.UserActivity.UserActivityInfoOperators;
import io.github.contextawareness.core.purposes.Purpose;

public class SimultaneousExamples {
    private UQI uqi;

    public SimultaneousExamples(Context context) {
        this.uqi = new UQI(context);
    }

    // Get the average loudness when the user is still.
    public void LoudnessOnStill() {
        HashMap<String, Function> fieldMap1 = new HashMap<>();
        fieldMap1.put("field1", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA));
        EnvironmentFactor factor1 = new EnvironmentFactor(Audio.recordPeriodic(1000, 3000), fieldMap1);

        HashMap<String, Function> fieldMap2 = new HashMap<>();
        fieldMap2.put("field2", UserActivityInfoOperators.isStill());
        EnvironmentFactor factor2 = new EnvironmentFactor(UserActivityInfo.asUpdates(uqi, DetectedActivity.STILL, 1000), fieldMap2);

        LinkedList<EnvironmentFactor> factors = new LinkedList<>();
        factors.add(factor1);
        factors.add(factor2);

        uqi.getData(Environment.asAnd(factors), Purpose.UTILITY("Awareness"))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        Log.d("Log", "Loudness: "+input.getAsDouble("field1").toString());

                    }
                });
    }



}
