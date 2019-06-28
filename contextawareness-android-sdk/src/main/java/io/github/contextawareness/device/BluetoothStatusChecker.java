package io.github.contextawareness.device;

import android.Manifest;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.DeviceUtils;

class BluetoothStatusChecker extends Function<Item,Boolean> {

    BluetoothStatusChecker() {
        this.addRequiredPermissions(Manifest.permission.BLUETOOTH);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        return DeviceUtils.isBluetoothConnected();
    }
}