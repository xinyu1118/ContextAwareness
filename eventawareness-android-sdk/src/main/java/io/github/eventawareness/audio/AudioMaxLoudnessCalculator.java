package io.github.eventawareness.audio;


import io.github.eventawareness.core.UQI;

/**
 * Calculate the maximum loudness of an audio field.
 * The loudness is an double number indicating the sound pressure level in dB.
 */
class AudioMaxLoudnessCalculator extends AudioProcessor<Double> {

    AudioMaxLoudnessCalculator(String audioDataField) {
        super(audioDataField);
    }

    @Override
    protected Double processAudio(UQI uqi, AudioData audioData) {
        return audioData.getMaxLoudness(uqi);
    }

}