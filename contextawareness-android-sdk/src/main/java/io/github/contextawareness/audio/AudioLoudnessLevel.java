package io.github.contextawareness.audio;

import android.util.Log;

import io.github.contextawareness.core.Operators;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Compare the average loudness with a threshold.
 */
class AudioLoudnessLevel extends AudioProcessor<Boolean> {
    private String operators;
    private Double threshold;

    AudioLoudnessLevel(String audioDataField, String operators, Double threshold) {
        super(audioDataField);
        this.operators = operators;
        this.threshold = threshold;
    }

    @Override
    protected Boolean processAudio(UQI uqi, AudioData audioData) {
        Boolean result = false;
        Double loudness = audioData.getLoudness(uqi);
        switch (operators) {
            case Operators.GTE:
                if (loudness >= threshold) result = true;
                break;
            case Operators.GT:
                if (loudness > threshold) result = true;
                break;
            case Operators.LTE:
                if (loudness <= threshold) result = true;
                break;
            case Operators.LT:
                if (loudness < threshold) result = true;
                break;
            case Operators.EQ:
                if (loudness == threshold) result = true;
                break;
            case Operators.NEQ:
                if (loudness != threshold) result = true;
                break;
            default:
                Log.d(Consts.LIB_TAG, "No matchable operators, please check it again.");
        }

        return result;
    }

}