package io.github.contextawareness;

import android.content.Context;
import android.util.Log;

import io.github.contextawareness.audio.Audio;
import io.github.contextawareness.audio.AudioOperators;
import io.github.contextawareness.core.Callback;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.Operators;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.location.Geolocation;
import io.github.contextawareness.location.GeolocationOperators;
import io.github.contextawareness.location.LatLon;

public class PersonalDataExamples {
    private UQI uqi;

    public PersonalDataExamples(Context context) {
        this.uqi = new UQI(context);
    }

    // Get the average loudness when it is greater than or equal to 30dB
    public void LoudnessLevel() {
        try {
            uqi.getData(Audio.recordPeriodic(1000, 3000), Purpose.UTILITY("Get loudness"))
                    .listening("contextSignal", AudioOperators.loudnessLevel(Operators.GTE, 30.0))
                    .setField("loudness", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA))
                    .forEach(new Callback<Item>() {
                        @Override
                        protected void onInput(Item input) {
                            if (input.getAsBoolean("contextSignal"))
                                Log.d("Log", "Loudness: "+String.valueOf(input.getAsDouble("loudness")));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the precise latitude and longitude when location is updated.
    // In China google services are not available, make sure 'Globals.LocationConfig.useGoogleService = false';
    public void LocationUpdates() {
        try {
            uqi.getData(Geolocation.asUpdates(1000, Geolocation.LEVEL_EXACT), Purpose.UTILITY("Get location"))
                    .setField("location", GeolocationOperators.getLatLon())
                    .forEach(new Callback<Item>() {
                        @Override
                        protected void onInput(Item input) {
                            LatLon latLon = input.getValueByField("location");
                            Log.d("Log", "location: "+String.valueOf(latLon.getLatitude())+", "+String.valueOf(latLon.getLongitude()));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
