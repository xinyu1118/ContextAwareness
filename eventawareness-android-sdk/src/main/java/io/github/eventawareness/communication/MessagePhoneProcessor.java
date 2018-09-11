package io.github.eventawareness.communication;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Process the phone field in a Message item.
 * @param <Tout> the phone type
 */
abstract class MessagePhoneProcessor<Tout> extends ItemOperator<Tout> {
    private final String contactField;

    MessagePhoneProcessor(String contactField) {
        this.contactField = Assertions.notNull("contactField", contactField);
        this.addParameters(contactField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        String contact = input.getValueByField(this.contactField);
        return this.processMessage(uqi, contact);
    }

    protected abstract Tout processMessage(UQI uqi, String contact);
}