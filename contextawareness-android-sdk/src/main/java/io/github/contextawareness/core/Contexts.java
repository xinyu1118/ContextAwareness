package io.github.contextawareness.core;


import io.github.contextawareness.utils.Logging;

public abstract class Contexts extends EventDrivenFunction<Void, ContextSignal> {

    protected transient volatile boolean isCancelled;
    protected transient volatile boolean isContextsAwared;

    public static final int AlwaysRepeat = 0;
    public static final int PassiveListening = 100;

    protected void init() {
        this.output = new ContextSignal(this.getUQI(), this);
        this.isCancelled = false;
        this.isContextsAwared = false;

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
            Logging.warn(this.getClass().getSimpleName() + " is outputting to an empty context signal.");
            return;
        }
        if (this.output.isClosed()) {
            if (!this.isCancelled) this.cancel(this.getUQI());
        }
        else this.output.write(item, this);
    }

    public abstract void setListeningParameters(UQI uqi, int numOfRecurrences, long interval);

    protected abstract void provide();

    protected void finish() {
        this.output(Item.EOS);
    }

}
