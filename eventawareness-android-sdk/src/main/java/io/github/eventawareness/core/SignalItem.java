package io.github.eventawareness.core;


import java.io.Serializable;

import io.github.eventawareness.utils.annotations.PSItemField;

public class SignalItem extends Item implements Serializable {

    @PSItemField(type = Long.class)
    public static final String TIMESTAMP = "timestamp";

    @PSItemField(type = SignalItem.class)
    public static final String CONTEXTS_SIGNAL = "contexts_signal";


    public SignalItem(long timestamp, boolean contextsSignal) {
        this.setFieldValue(TIMESTAMP, timestamp);
        this.setFieldValue(CONTEXTS_SIGNAL, contextsSignal);
    }


}
