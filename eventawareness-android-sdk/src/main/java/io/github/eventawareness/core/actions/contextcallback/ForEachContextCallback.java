package io.github.eventawareness.core.actions.contextcallback;


import io.github.eventawareness.core.ContextAction;
import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.utils.Assertions;


class ForEachContextCallback extends ContextAction {
    private final Function<Item, Void> itemCallback;

    ForEachContextCallback(Function<Item, Void> itemCallback) {
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