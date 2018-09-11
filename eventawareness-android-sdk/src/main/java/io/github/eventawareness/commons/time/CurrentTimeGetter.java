package io.github.eventawareness.commons.time;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.UQI;

/**
 * Generate a time tag string
 */
final class CurrentTimeGetter extends Function<Void, Long> {
    CurrentTimeGetter() {
    }

    @Override
    public Long apply(UQI uqi, Void input) {
        return System.currentTimeMillis();
    }
}
