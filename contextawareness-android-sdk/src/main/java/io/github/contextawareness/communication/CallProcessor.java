package io.github.contextawareness.communication;


import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

/**
 * Process the contact field in a Call item.
 * @param <Tout> the contact type
 */
abstract class CallProcessor<Tout> extends ItemOperator<Tout> {
    private final String contactField;

    CallProcessor(String contactField) {
        this.contactField = Assertions.notNull("contactField", contactField);
        this.addParameters(contactField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        String phones = input.getValueByField(this.contactField);
        return this.processCall(uqi, phones);
    }

    protected abstract Tout processCall(UQI uqi, String phones);
}