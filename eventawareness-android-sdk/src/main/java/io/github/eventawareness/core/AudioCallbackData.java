package io.github.eventawareness.core;


/**
 * Audio callback data containing several fine-grained fields.
 */
public class AudioCallbackData extends CallbackData {

    /**
     * Intermediate data to be called back, such as average or maximum loudness in dB.
     */
    public Double fieldValue;


    public AudioCallbackData() {
        this.TIME_CREATED = System.currentTimeMillis();
    }

    public void setFieldValue(Double fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void setEventType(String eventType) {
        this.EVENT_TYPE = eventType;
    }

}
