package io.github.eventawareness.audio;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Process the audio field in an item.
 */
abstract class AudioProcessor<Tout> extends ItemOperator<Tout> {

    private final String audioDataField;

    AudioProcessor(String audioDataField) {
        this.audioDataField = Assertions.notNull("audioDataField", audioDataField);
        this.addParameters(audioDataField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        AudioData audioData = input.getValueByField(this.audioDataField);
        return this.processAudio(uqi, audioData);
    }

    protected abstract Tout processAudio(UQI uqi, AudioData audioData);

}
