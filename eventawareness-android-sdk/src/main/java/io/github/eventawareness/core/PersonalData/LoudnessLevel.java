package io.github.eventawareness.core.PersonalData;


import android.Manifest;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.Operators;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Consts;
import io.github.eventawareness.utils.Globals;
import io.github.eventawareness.utils.StorageUtils;
import io.github.eventawareness.utils.TimeUtils;

/**
 * Compare the average loudness recorded from microphone with a threshold in dB.
 */
public class LoudnessLevel extends Contexts {

    private String comparisonOperator;
    private double threshold;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public LoudnessLevel(String comparisonOperator, double threshold) {
        this.comparisonOperator = comparisonOperator;
        this.threshold = threshold;
        this.addRequiredPermissions(Manifest.permission.RECORD_AUDIO);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    public SignalItem listening() {
        double loudness = 0.0;
        double duration = 1000;
        int BASE = 1;

        List<Integer> amplitudes = new ArrayList<>();

        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(Globals.AudioConfig.audioSource);
        recorder.setOutputFormat(Globals.AudioConfig.outputFormat);
        recorder.setAudioEncoder(Globals.AudioConfig.audioEncoder);

        String audioPath = "temp/audio_" + TimeUtils.getTimeTag();
        File tempAudioFile = StorageUtils.getValidFile(uqi.getContext(), audioPath, false);
        recorder.setOutputFile(tempAudioFile.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();

        long startTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > duration) {
                break;
            }
            amplitudes.add(recorder.getMaxAmplitude());
        }

        if (recorder != null) {
            double ratio = (double) recorder.getMaxAmplitude() / BASE;
            if (ratio > 1)
                loudness = 20 * Math.log10(ratio);
        }
        recorder.stop();
        recorder.reset();
        recorder.release();

//        If I use PrivacyStreams to get loudness, it will loop permission checking here
//        try {
//            loudness = audioUQI.getData(Audio.record(1000), Purpose.UTILITY("Get loudness"))
//                    .setField("loudness", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA))
//                    .getFirst("loudness");
//        } catch (PSException e) {
//            e.printStackTrace();
//        }

        this.isContextsAwared = false;
        switch (comparisonOperator) {
            case Operators.GTE:
                if (loudness >= threshold) {
                    Log.d(Consts.LIB_TAG, "Loudness GTE threshold.");
                    this.isContextsAwared = true;
                }
                break;
            case Operators.GT:
                if (loudness > threshold) {
                    Log.d(Consts.LIB_TAG, "Loudness GT threshold.");
                    this.isContextsAwared = true;
                }
                break;
            case Operators.LTE:
                if (loudness <= threshold) {
                    Log.d(Consts.LIB_TAG, "Loudness LTE threshold.");
                    this.isContextsAwared = true;
                }
                break;
            case Operators.LT:
                if (loudness < threshold) {
                    Log.d(Consts.LIB_TAG, "Loudness LT threshold.");
                    this.isContextsAwared = true;
                }
                break;
            case Operators.EQ:
                if (loudness == threshold) {
                    Log.d(Consts.LIB_TAG, "Loudness EQ threshold.");
                    this.isContextsAwared = true;
                }
                break;
            case Operators.NEQ:
                if (loudness != threshold) {
                    Log.d(Consts.LIB_TAG, "Loudness NEQ threshold.");
                    this.isContextsAwared = true;
                }
                break;
            default:
                Log.d(Consts.LIB_TAG, "No matchable operator, please select one from the predefined collection.");
        }

        return new SignalItem(System.currentTimeMillis(), this.isContextsAwared);

    }

    @Override
    protected void provide() {
        int recurrences = 0;
        long time_window = interval;
        while (!this.isCancelled) {
            SignalItem signalItem = listening();
            if (signalItem != null) this.output(signalItem);
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences >= numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                this.isCancelled = true;
            } else {
                try {
                    // If the context doesn't happen, enlarge the time window twice until it reaches
                    // 32 times of the initial interval.
                    if (!this.isContextsAwared && time_window != 32*interval) {
                        Thread.sleep(time_window);
                        time_window = 2 * time_window;
                    }
                    else {
                        Thread.sleep(time_window);
                        time_window = interval;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.finish();
    }

}
