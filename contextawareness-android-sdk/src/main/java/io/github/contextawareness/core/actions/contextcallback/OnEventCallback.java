package io.github.contextawareness.core.actions.contextcallback;


import android.util.Log;

import io.github.contextawareness.core.ContextAction;
import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.Assertions;
import io.github.contextawareness.utils.Consts;

class OnEventCallback extends ContextAction {

    private final String fieldToSelect;
    private final Function<Item, Void> fieldValueCallback;

    OnEventCallback(String fieldToSelect, Function<Item, Void> fieldValueCallback) {
        this.fieldToSelect = Assertions.notNull("fieldToSelect", fieldToSelect);
        this.fieldValueCallback = Assertions.notNull("fieldValueCallback", fieldValueCallback);
        this.addParameters(fieldToSelect, fieldValueCallback);
    }

    @Override
    protected void onInput(Item item) {
        if (item.isEndOfStream()) {
            this.finish();
            return;
        }
        Boolean fieldValue = item.getAsBoolean(this.fieldToSelect);
        if (fieldValue)
            this.fieldValueCallback.apply(this.getUQI(), item);
        else
            Log.d(Consts.LIB_TAG, "Contexts haven't happened yet.");
    }
}