package io.github.eventawareness.audio;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Calculate the max amplitude of an audio field.
 */
class AmplitudeToLoudnessConverter extends ItemOperator<Double> {

    private final String amplitudeField;

    AmplitudeToLoudnessConverter(String amplitudeField) {
        this.amplitudeField = Assertions.notNull("amplitudeField", amplitudeField);
        this.addParameters(amplitudeField);
    }

    @Override
    public Double apply(UQI uqi, Item item) {
        Number amplitude = item.getValueByField(this.amplitudeField);
        return AudioData.convertAmplitudeToLoudness(uqi, amplitude);
    }

}
