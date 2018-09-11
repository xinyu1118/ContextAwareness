package io.github.eventawareness.audio;


import android.Manifest;
import android.media.MediaRecorder;

import io.github.eventawareness.core.UQI;
import io.github.eventawareness.core.exceptions.PSException;
import io.github.eventawareness.core.PStreamProvider;
import io.github.eventawareness.utils.Globals;
import io.github.eventawareness.utils.StorageUtils;
import io.github.eventawareness.utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Record audio for a duration from microphone
 */
class AudioRecorder extends PStreamProvider {
    private final Long duration;

    AudioRecorder(long duration) {
        this.duration = duration;
        this.addParameters(duration);
        this.addRequiredPermissions(Manifest.permission.RECORD_AUDIO);
    }

    @Override
    protected void provide() {
        Audio audioItem = null;
        try {
            audioItem = recordAudio(this.getUQI(), this.duration);
            if (audioItem != null)
                this.output(audioItem);
        } catch (IOException e) {
            e.printStackTrace();
            this.raiseException(this.getUQI(), PSException.INTERRUPTED("AudioRecorder failed."));
        } catch (RuntimeException e) {
            e.printStackTrace();
            this.raiseException(this.getUQI(), PSException.INTERRUPTED("AudioRecorder failed. Perhaps the audio duration is too short."));
        }
        this.finish();
    }

    static Audio recordAudio(UQI uqi, long duration) throws IOException {
        List<Integer> amplitudes = new ArrayList<>();

        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(Globals.AudioConfig.audioSource);
        recorder.setOutputFormat(Globals.AudioConfig.outputFormat);
        recorder.setAudioEncoder(Globals.AudioConfig.audioEncoder);

        String audioPath = "temp/audio_" + TimeUtils.getTimeTag();
        File tempAudioFile = StorageUtils.getValidFile(uqi.getContext(), audioPath, false);
        recorder.setOutputFile(tempAudioFile.getAbsolutePath());

        recorder.prepare();
        recorder.start();   // Recording is now started

        long startTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > duration) {
                break;
            }
            amplitudes.add(recorder.getMaxAmplitude());
        }

        recorder.stop();
        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder.release(); // Now the object cannot be reused

        AudioData audioData = AudioData.newTempRecord(tempAudioFile, amplitudes);

        return new Audio(startTime, audioData);
    }
}
