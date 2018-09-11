package io.github.eventawareness.core.transformations.filter;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;

import static io.github.eventawareness.utils.Assertions.notNull;

/**
 * Keep the items that satisfy a predicate.
 */
final class PredicateFilter extends StreamFilter {
    private final Function<Item, Boolean> predicate;

    PredicateFilter(final Function<Item, Boolean> predicate) {
        this.predicate = notNull("predicate", predicate);
        this.addParameters(predicate);
    }

    @Override
    public boolean keep(Item item) {
        return this.predicate.apply(this.getUQI(), item);
    }

}
