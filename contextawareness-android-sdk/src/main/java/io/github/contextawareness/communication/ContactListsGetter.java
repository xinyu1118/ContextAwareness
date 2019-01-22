package io.github.contextawareness.communication;


import java.util.List;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.exceptions.PSException;
import io.github.contextawareness.core.purposes.Purpose;

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

