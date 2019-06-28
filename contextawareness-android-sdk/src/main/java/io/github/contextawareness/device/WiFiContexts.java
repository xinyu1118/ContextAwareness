package io.github.contextawareness.device;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class WiFiContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;

    WiFiContexts() {
        this.pStreamProvider = this.getProvider();
    }


    @Override
    public PStreamProvider getProvider() {
        return new DeviceStateUpdatesProvider(5000, DeviceState.Masks.WIFI_AP_LIST);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("wifi connected"))
                    .setField("context_signal", DeviceOperators.isWifiConnected());
        return this.pStream;
    }
}