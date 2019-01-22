package io.github.contextawareness.core.actions.contextcallback;


import io.github.contextawareness.core.ContextAction;
import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.Assertions;


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