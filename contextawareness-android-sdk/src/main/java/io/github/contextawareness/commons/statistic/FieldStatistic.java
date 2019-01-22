package io.github.contextawareness.commons.statistic;

import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.Assertions;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculate the statistic of a field in a stream
 */

abstract class FieldStatistic<Tout> extends StreamStatistic<Tout> {

    private final String numField;

    FieldStatistic(String numField) {
        this.numField = Assertions.notNull("numField", numField);
        this.addParameters(numField);
    }

    @Override
    protected final Tout calculate(List<Item> items) {
        List<Number> numList = new ArrayList<>();
        for (Item item : items) {
            Number fieldValue = item.getValueByField(numField);
            if (fieldValue != null) numList.add(fieldValue);
        }
        return this.processNumList(numList);
    }

    protected abstract Tout processNumList(List<Number> numList);
}
