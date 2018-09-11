package io.github.eventawareness.communication;


import java.util.List;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access contact-related operators.
 */
@PSOperatorWrapper
public class ContactOperators {

    /**
     * Get the email list from all contacts.
     *
     * @return the function
     */
    public static Function<Item, List<String>> getContactEmails() {
        String emailsField = Contact.EMAILS;
        return new ContactEmailGetter(emailsField);
    }

    /**
     * Get the phone list from all contacts.
     *
     * @return the function
     */
    public static Function<Item, List<String>> getContactPhones() {
        String phonesField = Contact.PHONES;
        return new ContactPhoneGetter(phonesField);
    }

    /**
     * Get contact lists.
     *
     * @return the function
     */
    public static Function<Item, List<Item>> getContactLists() {
        return new ContactListsGetter();
    }

}
