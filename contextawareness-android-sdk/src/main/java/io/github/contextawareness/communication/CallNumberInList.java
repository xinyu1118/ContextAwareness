package io.github.contextawareness.communication;

import java.util.List;

import io.github.contextawareness.core.UQI;

/**
 * Monitor the incoming call in a phone list.
 */
class CallNumberInList extends CallProcessor<Boolean> {
    private List<String> phoneList;

    CallNumberInList(String contactField, List<String> phoneList) {
        super(contactField);
        this.phoneList = phoneList;
    }

    @Override
    protected Boolean processCall(UQI uqi, String contact) {
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