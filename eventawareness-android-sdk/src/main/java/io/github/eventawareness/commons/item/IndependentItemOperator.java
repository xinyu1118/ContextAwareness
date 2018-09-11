package io.github.eventawareness.commons.item;

import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * A function that is independent from current item.
 */

class IndependentItemOperator<Tout> extends ItemOperator<Tout> {
    private Function<Void, Tout> voidInFunction;

    IndependentItemOperator(Function<Void, Tout> voidInFunction) {
        this.voidInFunction = Assertions.notNull("voidInFunction", voidInFunction);
        this.addParameters(voidInFunction);
    }

    @Override
    public Tout apply(UQI uqi, Item input) {
        return this.voidInFunction.apply(uqi, null);
    }
}
