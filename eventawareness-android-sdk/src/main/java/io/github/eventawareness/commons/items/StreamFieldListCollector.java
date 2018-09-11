package io.github.eventawareness.commons.items;

import io.github.eventawareness.commons.ItemsOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;

import java.util.ArrayList;
import java.util.List;

import static io.github.eventawareness.utils.Assertions.notNull;

/**
 * Select a field of each item in a stream and output to a list
 * Each item in the list is the field value in type TValue.
 */

class StreamFieldListCollector<TValue> extends ItemsOperator<List<TValue>> {
    private final String fieldToSelect;

    StreamFieldListCollector(String fieldToSelect) {
        this.fieldToSelect = notNull("fieldToSelect", fieldToSelect);
        this.addParameters(fieldToSelect);
    }

    @Override
    public List<TValue> apply(UQI uqi, List<Item> items) {
        List<TValue> result = new ArrayList<>();
        for (Item item : items) {
            TValue fieldValue = item.getValueByField(this.fieldToSelect);
            result.add(fieldValue);
        }
        return result;
    }

}
