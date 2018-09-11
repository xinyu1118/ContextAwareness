package io.github.eventawareness.commons.statistic;

import io.github.eventawareness.core.Item;

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
