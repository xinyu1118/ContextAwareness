package io.github.eventawareness.commons.items;

import io.github.eventawareness.commons.ItemsOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

import java.util.List;


/**
 * select an item from the items
 * return null if fails to find an item
 */

abstract class ByFieldItemSelector extends ItemsOperator<Item> {
    protected final String field;

    ByFieldItemSelector(String field) {
        this.field = Assertions.notNull("field", field);
        this.addParameters(field);
    }

    protected abstract Item selectFrom(List<Item> items);

    @Override
    public Item apply(UQI uqi, List<Item> input) {
        return this.selectFrom(input);
    }
}
