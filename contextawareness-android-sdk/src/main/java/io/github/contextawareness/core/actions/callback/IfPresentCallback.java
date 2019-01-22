package io.github.contextawareness.core.actions.callback;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStreamAction;
import io.github.contextawareness.utils.Assertions;

/**
 * Callback once an item is present in the stream.
 */

class IfPresentCallback extends PStreamAction {
    private final Function<Item, Void> itemCallback;

    IfPresentCallback(Function<Item, Void> itemCallback) {
        this.itemCallback = Assertions.notNull("itemCallback", itemCallback);
        this.addParameters(itemCallback);
    }

    @Override
    protected void onInput(Item item) {
        if (item.isEndOfStream()) {
            this.finish();
            return;
        }
        this.itemCallback.apply(this.getUQI(), item);
    }

}
