package io.github.eventawareness.core;


import android.util.Log;

import io.github.eventawareness.utils.Consts;

/**
 * Logical operation 'OR' for two contexts.
 */
public class OR extends Contexts {

    private Contexts contexts1;
    private Contexts contexts2;
    int recurrences;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public OR(Contexts contexts1, Contexts contexts2) {
        recurrences = 0;
        this.contexts1 = contexts1;
        this.contexts2 = contexts2;
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
        contexts1.setListeningParameters(uqi, numOfRecurrences, interval);
        contexts2.setListeningParameters(uqi, numOfRecurrences, interval);
    }

    @Override
    protected void provide() {
        contexts1.init();
        contexts2.init();

        while (!contexts1.isCancelled && !contexts2.isCancelled) {
            recurrences++;
            this.isContextsAwared = contexts1.isContextsAwared || contexts2.isContextsAwared;
            this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));

            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences >= numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                this.isCancelled = true;
                contexts1.isCancelled = true;
                contexts2.isCancelled = true;
            } else {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.finish();
        contexts1.finish();
        contexts2.finish();
    }
}
