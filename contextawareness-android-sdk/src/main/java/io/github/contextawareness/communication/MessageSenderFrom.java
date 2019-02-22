package io.github.contextawareness.communication;

import io.github.contextawareness.core.UQI;

/**
 * Monitor the incoming message from a sender.
 */
class MessageSenderFrom extends MessagePhoneProcessor<Boolean> {
    private String phoneNumber;

    MessageSenderFrom(String contactField, String phoneNumber) {
        super(contactField);
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Boolean processMessage(UQI uqi, String contact) {
        if (contact == null) return null;
        return phoneNumber.equals(contact)? true: false;
    }
}