package io.github.contextawareness.audio;


import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

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
