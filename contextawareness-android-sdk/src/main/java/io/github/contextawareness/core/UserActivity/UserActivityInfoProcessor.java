package io.github.contextawareness.core.UserActivity;


import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;

/**
 * Get device id
 */
class UserActivityInfoProcessor extends Function<Item, Boolean> {

    UserActivityInfoProcessor() {
//        this.addRequiredPermissions(Manifest.permission.ACCESS_WIFI_STATE);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        if (input != null && input.getAsBoolean("contexts_signal")) {
            return true;
        }
        return false;
    }
}