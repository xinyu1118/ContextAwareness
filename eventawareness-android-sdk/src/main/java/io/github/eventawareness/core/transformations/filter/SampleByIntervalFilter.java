package io.github.eventawareness.core.transformations.filter;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;

import static io.github.eventawareness.utils.Assertions.notNull;

/**
 * Sample the items in the stream.
 */
final class SampleByIntervalFilter extends StreamFilter {
    private final long minInterval;

    SampleByIntervalFilter(long minInterval) {
        this.minInterval = minInterval;
        this.addParameters(minInterval);
    }

    private transient long lastItemTime = 0;

    @Override
    public boolean keep(Item item) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastItemTime < minInterval) return false;
        lastItemTime = currentTime;
        return true;
    }

}
