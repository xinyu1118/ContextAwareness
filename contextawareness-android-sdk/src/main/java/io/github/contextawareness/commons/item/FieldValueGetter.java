package io.github.contextawareness.commons.item;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;

import static io.github.contextawareness.utils.Assertions.notNull;

/**
 * A function that gets the value of a field
 */

class FieldValueGetter<TValue> extends ItemOperator<TValue> {
    private final String fieldToGet;

    FieldValueGetter(String fieldToGet) {
        this.fieldToGet = notNull("fieldToGet", fieldToGet);
        this.addParameters(fieldToGet);
    }

    @Override
    public TValue apply(UQI uqi, Item input) {
        return input.getValueByField(this.fieldToGet);
    }
}
