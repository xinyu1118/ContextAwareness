package io.github.contextawareness.communication;


import io.github.contextawareness.core.UQI;

/**
 * Get the phone number from incoming messages.
 */
class MessagePhoneGetter extends MessagePhoneProcessor<String> {

    MessagePhoneGetter(String contactField) {
        super(contactField);
    }

    @Override
    protected String processMessage(UQI uqi, String contact) {
        if (contact == null) return null;
        return contact;
    }
}
