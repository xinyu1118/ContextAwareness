package io.github.contextawareness.commons.string;

import io.github.contextawareness.utils.Assertions;

/**
 * A function that gets the index of a certain substring.
 */
final class StringIndexOfOperator extends StringProcessor<Integer> {

    private final String searchString;

    StringIndexOfOperator(String stringField, String searchString) {
        super(stringField);
        this.searchString = Assertions.notNull("searchString", searchString);
        this.addParameters(searchString);
    }

    @Override
    protected Integer processString(String stringValue) {
        if (stringValue == null) return -1;
        return stringValue.indexOf(this.searchString);
    }
}
