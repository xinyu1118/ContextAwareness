package io.github.contextawareness.device;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.utils.annotations.PSOperatorWrapper;

/**
 * A wrapper class of device-related functions.
 */
@PSOperatorWrapper
public class DeviceOperators {
    /**
     * Get device id.
     *
     * @return the function.
     */
    public static Function<Void, String> getDeviceId() {
        return new DeviceIdGetter();
    }

    /**
     * Check if wifi is connected.
     *
     * @return the function.
     */
    // @RequiresPermission(value = Manifest.permission.ACCESS_WIFI_STATE)
    public static Function<Item, Boolean> isWifiConnected() {
        return new WifiStatusChecker();
    }

    public static Function<Item, Boolean> isLowBattery(float threshold) {
        return new BatteryChecker(threshold);
    }

    public static Function<Item, Boolean> isLowBattery(float lowerBound, float upperBound) {
        return new BatteryChecker(lowerBound, upperBound);
    }

    public static Function<Item, Boolean> isBluetoothConnected() {
        return new BluetoothStatusChecker();
    }

    public static Function<Item, Boolean> isScreenOn() {
        return new ScreenStatusChecker();
    }

    public static Function<Item, Boolean> isDeviceInteractive() {
        return new DeviceActiveChecker();
    }

}
