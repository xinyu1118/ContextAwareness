package io.github.eventawareness.core.actions.contextcallback;


import io.github.eventawareness.core.ContextAction;
import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.utils.Assertions;

class ForEachFieldContextCallback<TValue, Void> extends ContextAction {

    private final String fieldToSelect;
    private final Function<TValue, Void> fieldValueCallback;

    ForEachFieldContextCallback(String fieldToSelect, Function<TValue, Void> fieldValueCallback) {
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
        TValue fieldValue = item.getValueByField(this.fieldToSelect);
        this.fieldValueCallback.apply(this.getUQI(), fieldValue);
    }

}
