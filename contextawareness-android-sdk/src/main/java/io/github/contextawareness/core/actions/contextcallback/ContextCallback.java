package io.github.contextawareness.core.actions.contextcallback;


import io.github.contextawareness.core.ContextAction;
import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStreamAction;
import io.github.contextawareness.core.actions.callback.OnFieldChangeCallback;
import io.github.contextawareness.utils.annotations.PSOperatorWrapper;

@PSOperatorWrapper
public class ContextCallback  {

    public static ContextAction onEvent(String fieldToSelect, Function<Item, Void> itemCallback) {
        //return new ForEachContextCallback(itemCallback);
        return new OnEventCallback(fieldToSelect, itemCallback);
    }

    public static ContextAction forEach(Function<Item, Void> itemCallback) {
        return new ForEachContextCallback(itemCallback);
    }

    public static <TValue> ContextAction forEachField(String fieldToSelect, Function<TValue, Void> fieldValueCallback) {
        return new ForEachFieldContextCallback<>(fieldToSelect, fieldValueCallback);
    }

    public static <TValue> ContextAction onFieldChange(String fieldToSelect, Function<TValue, Void> fieldValueCallback) {
        return new OnFieldChangeContextCallback<>(fieldToSelect, fieldValueCallback);
    }


}
