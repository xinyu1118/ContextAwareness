package io.github.eventawareness.commons.logic;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Compute AND result of two boolean functions.
 */
class AndOperator extends LogicOperator {

    private Function<Item, Boolean> function1;
    private Function<Item, Boolean> function2;

    AndOperator(Function<Item, Boolean> function1, Function<Item, Boolean> function2) {
        this.function1 = Assertions.notNull("function1", function1);
        this.function2 = Assertions.notNull("function2", function2);
        this.addParameters(function1, function2);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        boolean result1 = this.function1.apply(uqi, input);
        boolean result2 = this.function2.apply(uqi, input);
        return result1 && result2;
    }
}
