package io.github.eventawareness.communication;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

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