package io.github.contextawareness.core.items;

import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.utils.annotations.PSItem;
import io.github.contextawareness.utils.annotations.PSItemField;

/**
 * An empty item.
 */
@PSItem
public class EmptyItem extends Item {
    public EmptyItem() {
    }

    /**
     * Provide a live stream of EmptyItems. The interval between each two items is a given value.
     *
     * @return the provider function
     */
    public static PStreamProvider asUpdates(long interval) {
        return new EmptyItemUpdatesProvider(interval);
    }

    public static PStreamProvider asUpdates() {
        return new EmptyItemProvider();
    }

}
