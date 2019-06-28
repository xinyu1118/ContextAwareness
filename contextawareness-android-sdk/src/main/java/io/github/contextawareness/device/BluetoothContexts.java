package io.github.contextawareness.device;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class BluetoothContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;

    BluetoothContexts() {
        this.pStreamProvider = this.getProvider();
    }

    @Override
    public PStreamProvider getProvider() {
        return new DeviceStateUpdatesProvider(5000, DeviceState.Masks.BLUETOOTH_DEVICE_LIST);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("bluetooth connected"))
                .setField("context_signal", DeviceOperators.isBluetoothConnected());
        return this.pStream;
    }
}
