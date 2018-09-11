package io.github.eventawareness.communication;


import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access call-related operators.
 */
@PSOperatorWrapper
public class CallOperators {

    /**
     * Get the phone number from incoming calls.
     *
     * @return the function
     */
    public static Function<Item, String> callerIdentification() {
        String contactField = Call.CONTACT;
        return new CallNumberGetter(contactField);
    }

}