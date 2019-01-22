package io.github.contextawareness.communication;


import java.util.List;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

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
