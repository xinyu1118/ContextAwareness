package io.github.eventawareness.device;

import android.Manifest;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.DeviceUtils;

/**
 * Get device id
 */
class WifiStatusChecker extends Function<Void, Boolean> {

    WifiStatusChecker() {
        this.addRequiredPermissions(Manifest.permission.ACCESS_WIFI_STATE);
    }

    @Override
    public Boolean apply(UQI uqi, Void input) {
        return DeviceUtils.isWifiConnected(uqi.getContext());
    }
}
