package io.github.eventawareness.core;

import android.util.Log;

import io.github.eventawareness.utils.Consts;

/**
 * Logical operation 'NOT' for one contexts instance.
 */
public class NOT extends Contexts {

    private Contexts contexts;
    int recurrences;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    public NOT(Contexts contexts) {
        recurrences = 0;
        this.contexts = contexts;
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
        contexts.setListeningParameters(uqi, numOfRecurrences, interval);
    }

    @Override
    protected void provide() {
        contexts.init();

        while (!contexts.isCancelled) {
            recurrences++;
            this.isContextsAwared = !contexts.isContextsAwared;
            this.output(new SignalItem(System.currentTimeMillis(), this.isContextsAwared));

            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences >= numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                this.isCancelled = true;
                contexts.isCancelled = true;
            } else {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.finish();
        contexts.finish();
    }
}
