package io.github.eventawareness.core;

import io.github.eventawareness.core.EventDrivenFunction;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.PStream;
import io.github.eventawareness.core.Stream;
import io.github.eventawareness.utils.Logging;

/**
 * A PStreamProvider is a function that produces a stream.
 */
public abstract class PStreamProvider extends EventDrivenFunction<Void, PStream> {
    protected void init() {
        this.output = new PStream(this.getUQI(), this);
        this.isCancelled = false;
        Thread providingThread = new Thread() {
            @Override
            public void run() {
                provide();
            }
        };
        providingThread.start();
    }

    protected final void output(Item item) {
        if (this.output == null) {
            Logging.warn(this.getClass().getSimpleName() + " is outputting to an empty stream.");
            return;
        }
        if (this.output.isClosed()) {
            if (!this.isCancelled) this.cancel(this.getUQI());
        }
        else this.output.write(item, this);
    }

    /**
     * Provide stream data
     * This method will be running in background, and should be stopped when isCancelled turns true.
     */
    protected abstract void provide();

    protected void finish() {
        this.output(Item.EOS);
    }
}
