package io.github.eventawareness.core.PhoneState;


import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Consts;

/**
 * Listen to the headphones status, i.e. HeadphonesPlugged()
 * will return notification when the headphones are plugged in.
 */
public class HeadphonesPlugged extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public HeadphonesPlugged() {
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        int recurrences = 0;
        SignalItem signalItem;

        while (!this.isCancelled) {
            recurrences++;

            AudioManager audioManager = (AudioManager) uqi.getContext().getSystemService(Context.AUDIO_SERVICE);
            assert audioManager != null;
            boolean isHeadphonesSetOn = audioManager.isWiredHeadsetOn();
            if (isHeadphonesSetOn) {
                Log.d(Consts.LIB_TAG, "Headphone plugged.");
                this.isContextsAwared = true;
            }
            if (!isHeadphonesSetOn) {
                Log.d(Consts.LIB_TAG, "Headphones unplugged.");
                this.isContextsAwared = false;
            }

            signalItem = new SignalItem(System.currentTimeMillis(), this.isContextsAwared);
            if (signalItem != null) this.output(signalItem);

            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences >= numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                this.isCancelled = true;
            } else {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.finish();

    }

}
