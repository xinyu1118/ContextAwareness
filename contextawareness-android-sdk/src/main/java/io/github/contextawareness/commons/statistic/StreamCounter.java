package io.github.contextawareness.commons.statistic;

import io.github.contextawareness.core.Item;

import java.util.List;

/**
 * Count the number of items in the stream
 */
final class StreamCounter extends StreamStatistic<Integer> {
    @Override
    public Integer calculate(List<Item> items) {
        return items.size();
    }
}
