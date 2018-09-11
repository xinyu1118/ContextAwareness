package io.github.eventawareness.commons.item;

import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;

import java.util.Map;

import static io.github.eventawareness.utils.Assertions.notNull;

/**
 * Get the value of a field.
 */

final class SubItemGetter extends ItemOperator<Item> {
    private final String subItemField;

    SubItemGetter(String subItemField) {
        this.subItemField = notNull("subItemField", subItemField);
        this.addParameters(subItemField);
    }

    @Override
    public Item apply(UQI uqi, Item input) {
        Map<String, Object> subItemMap = input.getValueByField(this.subItemField);
        return new Item(subItemMap);
    }

}
