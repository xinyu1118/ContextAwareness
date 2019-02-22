package io.github.contextawareness.communication;

import java.util.List;

import io.github.contextawareness.core.UQI;

/**
 * Monitor the incoming message in a list.
 */
class MessageSenderInList extends MessagePhoneProcessor<Boolean> {
    private List<String> phoneList;

    MessageSenderInList(String contactField, List<String> phoneList) {
        super(contactField);
        this.phoneList = phoneList;
    }

    @Override
    protected Boolean processMessage(UQI uqi, String contact) {
        Boolean result = false;
        if (contact == null) return null;
        for (String phone : phoneList) {
            if (phone.equals(contact)) {
                result = true;
                break;
            }
        }
        return result;
    }
}