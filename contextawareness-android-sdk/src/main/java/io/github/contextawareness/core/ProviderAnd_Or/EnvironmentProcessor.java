package io.github.contextawareness.core.ProviderAnd_Or;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;

public class EnvironmentProcessor extends Function<Item, String> {

    EnvironmentProcessor() {
//        this.addRequiredPermissions(Manifest.permission.ACCESS_WIFI_STATE);
    }

    @Override
    public String apply(UQI uqi, Item input) {
        return input.toString();
    }
}