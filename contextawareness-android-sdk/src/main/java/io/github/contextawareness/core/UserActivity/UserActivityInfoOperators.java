package io.github.contextawareness.core.UserActivity;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.annotations.PSOperatorWrapper;

/**
 * A wrapper class of UserActivityInfoOperators.
 */

@PSOperatorWrapper
public class UserActivityInfoOperators {

    /**
     * Do Test Awareness
     *
     * @return the function.
     */
    // @RequiresPermission(value = Manifest.permission.ACCESS_WIFI_STATE)
    public static Function<Item, Boolean> isStill() {
        return new UserActivityInfoProcessor();
    }
}