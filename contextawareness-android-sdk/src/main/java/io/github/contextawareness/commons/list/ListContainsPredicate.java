package io.github.contextawareness.commons.list;

import io.github.contextawareness.utils.Assertions;

import java.util.List;

/**
 * Make list-related comparisons on field values.
 */
final class ListContainsPredicate extends ListProcessor<Boolean> {

    private final Object value;

    ListContainsPredicate(final String listField, final Object value) {
        super(listField);
        this.value = Assertions.notNull("value", value);
        this.addParameters(value);
    }

    @Override
    protected Boolean processList(List<Object> list) {
        return list.contains(this.value);
    }

}
