package io.github.contextawareness.communication;


import java.util.List;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access call-related operators.
 */
@PSOperatorWrapper
public class CallOperators {

    /**
     * Caller from a phone number.
     *
     * @param phoneNumber the phone number
     * @return the function
     */
    public static Function<Item, Boolean> callerFrom(String phoneNumber) {
        String contactField = Call.CONTACT;
        return new CallNumberFrom(contactField, phoneNumber);
    }

    /**
     * Caller in a phone list.
     *
     * @param phoneList the phone list
     * @return the function
     */
    public static Function<Item, Boolean> callerInList(List<String> phoneList) {
        String contactField = Call.CONTACT;
        return new CallNumberInList(contactField, phoneList);
    }

}