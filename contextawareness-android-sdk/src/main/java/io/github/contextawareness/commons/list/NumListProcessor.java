package io.github.contextawareness.commons.list;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

import java.util.List;

/**
 * Process the list field in an item.
 */
abstract class NumListProcessor<Tout> extends ItemOperator<Tout> {

    private final String numListField;

    NumListProcessor(String numListField) {
        this.numListField = Assertions.notNull("numListField", numListField);
        this.addParameters(numListField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        List<Number> numList = input.getValueByField(this.numListField);
        return this.processNumList(numList);
    }

    protected abstract Tout processNumList(List<Number> numList);

}
