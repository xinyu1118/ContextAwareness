package io.github.eventawareness.core.items;

import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.PStreamProvider;
import io.github.eventawareness.utils.annotations.PSItem;

/**
 * An empty item.
 */
@PSItem
public class EmptyItem extends Item {

    /**
     * Provide a live stream of EmptyItems. The interval between each two items is a given value.
     *
     * @return the provider function
     */
    public static PStreamProvider asUpdates(long interval) {
        return new EmptyItemUpdatesProvider(interval);
    }
}
