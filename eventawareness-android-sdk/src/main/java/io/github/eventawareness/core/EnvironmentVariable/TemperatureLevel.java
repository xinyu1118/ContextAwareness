package io.github.eventawareness.core.EnvironmentVariable;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.Operators;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Consts;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Compare the current temperature with a threshold in centigrade.
 */
public class TemperatureLevel extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    private String comparisonOperator;
    private float threshold;
    int recurrences;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorListener;

    public TemperatureLevel(String comparisonOperator, float threshold) {
        recurrences = 0;
        this.comparisonOperator = comparisonOperator;
        this.threshold = threshold;
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {

        sensorManager = (SensorManager) uqi.getContext().getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Log.d("Log", "Current temperature: "+String.valueOf(sensorEvent.values[0]));
                Float temperature = sensorEvent.values[0];

                switch (comparisonOperator) {
                    case Operators.GTE:
                        if (temperature >= threshold) {
                            Log.d(Consts.LIB_TAG, "Temperature GTE the threshold.");
                            TemperatureLevel.this.isContextsAwared = true;
                        }
                        break;
                    case Operators.GT:
                        if (temperature > threshold) {
                            Log.d(Consts.LIB_TAG, "Temperature GT the threshold.");
                            TemperatureLevel.this.isContextsAwared = true;
                        }
                        break;
                    case Operators.LTE:
                        if (temperature <= threshold) {
                            Log.d(Consts.LIB_TAG, "Temperature LTE the threshold.");
                            TemperatureLevel.this.isContextsAwared = true;
                        }
                        break;
                    case Operators.LT:
                        if (temperature < threshold) {
                            Log.d(Consts.LIB_TAG, "Temperature LT the threshold.");
                            TemperatureLevel.this.isContextsAwared = true;
                        }
                        break;
                    case Operators.EQ:
                        if (temperature == threshold) {
                            Log.d(Consts.LIB_TAG, "Temperature EQ the threshold.");
                            TemperatureLevel.this.isContextsAwared = true;
                        }
                        break;
                    case Operators.NEQ:
                        if (temperature != threshold) {
                            Log.d(Consts.LIB_TAG, "Temperature NEQ the threshold.");
                            TemperatureLevel.this.isContextsAwared = true;
                        }
                        break;
                    default:
                        Log.d(Consts.LIB_TAG, "No matchable operator, please select one from the predefined collection.");
                }

                TemperatureLevel.this.output(new SignalItem(System.currentTimeMillis(), TemperatureLevel.this.isContextsAwared));
                TemperatureLevel.this.isContextsAwared = false;
                recurrences++;
                if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences >= numOfRecurrences) {
                    Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                    TemperatureLevel.this.isCancelled = true;
                    sensorManager.unregisterListener(sensorListener);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(sensorListener, sensor, (int) interval);
    }
}
