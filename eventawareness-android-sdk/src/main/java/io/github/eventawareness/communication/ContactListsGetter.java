package io.github.eventawareness.communication;


import java.util.List;

import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.core.exceptions.PSException;
import io.github.eventawareness.core.purposes.Purpose;

/**
 * Get a list of contact items.
 */
class ContactListsGetter extends ItemOperator<List<Item>> {

    @Override
    public List<Item> apply(UQI uqi, Item input) {
        List<Item> contactLists = null;
        try {
            contactLists = uqi.getData(Contact.getAll(), Purpose.UTILITY("Get contact lists."))
                    .asList();
        } catch (PSException e) {
            e.printStackTrace();
        }
        return contactLists;
    }
}

