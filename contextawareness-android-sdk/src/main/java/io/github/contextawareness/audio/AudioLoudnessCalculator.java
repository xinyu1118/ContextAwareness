package io.github.contextawareness.audio;


import io.github.contextawareness.core.UQI;

/**
 * Calculate the loudness of an audio field.
 * The loudness is an double number indicating the sound pressure level in dB.
 */
class AudioLoudnessCalculator extends AudioProcessor<Double> {

    AudioLoudnessCalculator(String audioDataField) {
        super(audioDataField);
    }

    @Override
    protected Double processAudio(UQI uqi, AudioData audioData) {
        return audioData.getLoudness(uqi);
    }

}
