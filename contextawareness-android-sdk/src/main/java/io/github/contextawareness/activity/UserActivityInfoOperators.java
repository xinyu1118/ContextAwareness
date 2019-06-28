package io.github.contextawareness.activity;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.annotations.PSOperatorWrapper;

/**
 * A wrapper class of UserActivityInfoOperators.
 */

@PSOperatorWrapper
public class UserActivityInfoOperators {

    public static Function<Item, Boolean> recognition(int queryActivity) {
        return new UserActivityInfoProcessor(queryActivity);
    }
}