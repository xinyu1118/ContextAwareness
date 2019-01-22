package io.github.contextawareness.core.actions.contextcallback;


import io.github.contextawareness.core.ContextAction;
import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.Assertions;

class OnFieldChangeContextCallback<TValue, Void> extends ContextAction {
    private final String fieldToSelect;
    private final Function<TValue, Void> fieldValueCallback;

    OnFieldChangeContextCallback(String fieldToSelect, Function<TValue, Void> fieldValueCallback) {
        this.fieldToSelect = Assertions.notNull("fieldToSelect", fieldToSelect);
        this.fieldValueCallback = Assertions.notNull("fieldValueCallback", fieldValueCallback);
        this.addParameters(fieldToSelect, fieldValueCallback);
    }

    private transient TValue lastFieldValue;
    @Override
    protected void onInput(Item item) {
        if (item.isEndOfStream()) {
            this.finish();
            return;
        }
        TValue fieldValue = item.getValueByField(this.fieldToSelect);
        if (fieldValue == null) return;
        if (fieldValue.equals(lastFieldValue)) return;
        this.fieldValueCallback.apply(this.getUQI(), fieldValue);
        this.lastFieldValue = fieldValue;
    }

}