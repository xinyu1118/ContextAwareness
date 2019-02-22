package io.github.contextawareness.location;


import android.util.Log;

import io.github.contextawareness.core.Operators;
import io.github.contextawareness.utils.Consts;

/**
 * Calculate average speed in m/s.
 */
public class LocationSpeedCalculator extends SpeedProcessor<Boolean> {
    private String operators;
    private Float threshold;

    LocationSpeedCalculator(String speedField, String operators, Float threshold) {
        super(speedField);
        this.operators = operators;
        this.threshold = threshold;
    }

    @Override
    protected Boolean processSpeed(Float speed) {
        if (speed == null) return null;

        Boolean result = false;
        switch (operators) {
            case Operators.GTE:
                if (speed >= threshold) result = true;
                break;
            case Operators.GT:
                if (speed > threshold) result = true;
                break;
            case Operators.LTE:
                if (speed <= threshold) result = true;
                break;
            case Operators.LT:
                if (speed < threshold) result = true;
                break;
            case Operators.EQ:
                if (speed == threshold) result = true;
                break;
            case Operators.NEQ:
                if (speed != threshold) result = true;
                break;
            default:
                Log.d(Consts.LIB_TAG, "No matchable operators, please check it again.");
        }

        return result;
    }
}