package io.github.contextawareness.communication;


import io.github.contextawareness.core.UQI;

/**
 * Monitor the incoming call from a phone number.
 */
class CallNumberFrom extends CallProcessor<Boolean> {
    private String phoneNumber;

    CallNumberFrom(String contactField, String phoneNumber) {
        super(contactField);
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Boolean processCall(UQI uqi, String contact) {
        if (contact == null) return null;
        return contact.equals(phoneNumber)? true: false;
    }

}