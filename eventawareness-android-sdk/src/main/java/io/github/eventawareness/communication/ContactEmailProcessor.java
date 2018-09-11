package io.github.eventawareness.communication;


import java.util.List;

import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Process the email field in a Contact field.
 * @param <Tout> the email type
 */
abstract class ContactEmailProcessor<Tout> extends ItemOperator<Tout> {
    private final String emailsField;

    ContactEmailProcessor(String emailsField) {
        this.emailsField = Assertions.notNull("emailsField", emailsField);
        this.addParameters(emailsField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        List<String> emails = input.getValueByField(this.emailsField);
        return this.processCall(uqi, emails);
    }

    protected abstract Tout processCall(UQI uqi, List<String> emails);
}
