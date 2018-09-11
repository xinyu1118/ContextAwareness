package io.github.eventawareness.commons.comparison;

import java.util.List;

import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;

import static io.github.eventawareness.utils.Assertions.notNull;

/**
 * Make comparisons on field value with a list.
 */
abstract class ItemFieldListOperator<TValue> extends ItemOperator<Boolean> {
    protected final String operator;
    protected final String field;
    protected final List<TValue> valueToCompare;

    ItemFieldListOperator(final String operator, final String field, final List<TValue> valueToCompare) {
        this.operator = notNull("operator", operator);
        this.field = notNull("field", field);
        this.valueToCompare = valueToCompare;
        this.addParameters(operator, field, valueToCompare);
    }

    public boolean test(Item item) {
        if (item == null) return false;
        TValue fieldValue = item.getValueByField(this.field);
        return testField(fieldValue);
    }

    protected abstract boolean testField(TValue fieldValue);

    public final Boolean apply(UQI uqi, Item input) {
        return this.test(input);
    }
}