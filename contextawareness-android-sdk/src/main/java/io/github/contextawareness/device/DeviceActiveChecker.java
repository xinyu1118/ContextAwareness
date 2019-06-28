package io.github.contextawareness.device;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.DeviceUtils;

class DeviceActiveChecker extends Function<Item, Boolean> {

    DeviceActiveChecker() {
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        return DeviceUtils.isDeviceInteractive(uqi.getContext());
    }
}