package io.github.eventawareness.core;


import io.github.eventawareness.commons.debug.DebugOperators;
import io.github.eventawareness.core.actions.contextcallback.ContextCallback;

public class ContextSignal extends Stream {

    private Contexts contextsProvider;

    @Override
    public Function<Void, ContextSignal> getStreamProvider() {
        return this.contextsProvider;
    }

    public ContextSignal(UQI uqi, Contexts contextsProvider) {
        super(uqi);
        this.contextsProvider = contextsProvider;
    }

    public ContextSignal listening(int numOfRecurrences, long interval) {
        contextsProvider.setListeningParameters(this.getUQI(), numOfRecurrences, interval);
        return new ContextSignal(this.getUQI(), contextsProvider);
    }

    public void output(ContextAction contextAction) {
        this.getUQI().evaluate(this.getStreamProvider().compound(contextAction), true);
    }

    // *****************************
    // Output functions
    // Output functions are used to output the context awareness results
    public void debug() {
        this.forEach(DebugOperators.<Item>debug());
    }

    public void onEvent(Function<Item, Void> callback) {
        String fieldToSelect = "contexts_signal";
        this.output(ContextCallback.onEvent(fieldToSelect, callback));
    }

    public void forEach(Function<Item, Void> callback) {
        this.output(ContextCallback.forEach(callback));
    }

    public <TValue> void forEach(String fieldToSelect, Function<TValue, Void> callback) {
        this.output(ContextCallback.forEachField(fieldToSelect, callback));
    }

    public <TValue> void onChange(String fieldToSelect, Function<TValue, Void> callback) {
        this.output(ContextCallback.onFieldChange(fieldToSelect, callback));
    }

}


