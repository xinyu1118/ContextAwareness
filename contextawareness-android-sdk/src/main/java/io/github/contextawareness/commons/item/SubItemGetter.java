package io.github.contextawareness.commons.item;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;

import java.util.Map;

import static io.github.contextawareness.utils.Assertions.notNull;

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
