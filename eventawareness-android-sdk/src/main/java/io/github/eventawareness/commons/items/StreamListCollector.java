package io.github.eventawareness.commons.items;

import io.github.eventawareness.commons.ItemsOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;

import java.util.List;

/**
 * Collect the stream to a list.
 * Each item in the list is an instance of Item.
 */

class StreamListCollector extends ItemsOperator<List<Item>> {
    @Override
    public List<Item> apply(UQI uqi, List<Item> items) {
        return items;
    }

}
