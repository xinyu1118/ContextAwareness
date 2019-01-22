package io.github.contextawareness.commons.arithmetic;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

/**
 * Process the number field in an item.
 */
abstract class ArithmeticOperator<Tout> extends ItemOperator<Tout> {

    private final String numField;

    ArithmeticOperator(String numField) {
        this.numField = Assertions.notNull("numField", numField);
        this.addParameters(numField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        Number number = input.getValueByField(this.numField);
        return this.processNum(number);
    }

    protected abstract Tout processNum(Number number);
}
