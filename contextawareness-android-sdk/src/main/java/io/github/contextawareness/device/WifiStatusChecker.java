package io.github.contextawareness.device;

import android.Manifest;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.DeviceUtils;

/**
 * Get device id
 */
class WifiStatusChecker extends Function<Item, Boolean> {

    WifiStatusChecker() {
        this.addRequiredPermissions(Manifest.permission.ACCESS_WIFI_STATE);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        return DeviceUtils.isWifiConnected(uqi.getContext());
    }
}
