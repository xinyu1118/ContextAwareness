package io.github.contextawareness.audio;


import io.github.contextawareness.core.UQI;

import java.util.List;

/**
 * Get the amplitude samples of the audio in an audio field.
 */
class AudioAmplitudeSamplesGetter extends AudioProcessor<List<Integer>> {

    AudioAmplitudeSamplesGetter(String audioDataField) {
        super(audioDataField);
    }

    @Override
    protected List<Integer> processAudio(UQI uqi, AudioData audioData) {
        return audioData.getAmplitudeSamples();
    }

}
