package io.github.contextawareness.core.ProviderAnd_Or;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.annotations.PSOperatorWrapper;


/**
 * A wrapper class of EnvironmentOperators.
 */

@PSOperatorWrapper
public class EnvironmentOperators {

    /**
     * Do Test immediateDataReturn
     *
     * @return the function.
     */
    // @RequiresPermission(value = Manifest.permission.ACCESS_WIFI_STATE)
    public static Function<Item, String> immediateDataReturn() {
        return new EnvironmentProcessor();
    }
}