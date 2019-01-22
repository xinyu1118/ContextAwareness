package io.github.contextawareness.core.transformations.reorder;

import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.Assertions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Sort the items in stream by the values of a field.
 */
class ByFieldStreamSorter extends StreamReorder {
    private final String fieldToSort;

    ByFieldStreamSorter(final String fieldToSort) {
        this.fieldToSort = Assertions.notNull("fieldToSort", fieldToSort);
        this.addParameters(fieldToSort);
    }

    @Override
    protected void reorder(List<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                Comparable value1 = item1.getValueByField(fieldToSort);
                Comparable value2 = item2.getValueByField(fieldToSort);
                return value1.compareTo(value2);
            }
        });
    }
}
