package io.github.contextawareness.communication;


import java.util.List;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access message-related operators.
 */
@PSOperatorWrapper
public class MessageOperators {

    /**
     * Message sender from a phone number.
     *
     * @param phoneNumber the phone number
     * @return the function
     */
    public static Function<Item, Boolean> SenderFrom(String phoneNumber) {
        String contactField = Message.CONTACT;
        return new MessageSenderFrom(contactField, phoneNumber);
    }

    /**
     * Message sender in a phone list.
     *
     * @param phoneList the phone list
     * @return the function
     */
    public static Function<Item, Boolean> SenderInList(List<String> phoneList) {
        String contactField = Message.CONTACT;
        return new MessageSenderInList(contactField, phoneList);
    }

    /**
     * Get incoming message content.
     *
     * @return the function
     */
    public static Function<Item, String> getMessageContent() {
        String contentField = Message.CONTENT;
        return new MessageContentGetter(contentField);
    }

}
