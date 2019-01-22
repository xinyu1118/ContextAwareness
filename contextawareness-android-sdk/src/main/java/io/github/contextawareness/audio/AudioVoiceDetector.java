package io.github.contextawareness.audio;


import io.github.contextawareness.core.UQI;

/**
 * Detect human voice from the audio.
 * Return true if there is human voice.
 */
class AudioVoiceDetector extends AudioProcessor<Boolean> {

    AudioVoiceDetector(String audioDataField) {
        super(audioDataField);
    }

    @Override
    protected Boolean processAudio(UQI uqi, AudioData audioData) {
        return audioData.hasHumanVoice(uqi);
    }

}
