package io.github.contextawareness.device;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class ScreenContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;

    ScreenContexts() {
        this.pStreamProvider = this.getProvider();
    }

    @Override
    public PStreamProvider getProvider() {
        return new DeviceStateUpdatesProvider(1000, DeviceState.Masks.SCREEN_STATE);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("screen on"))
                .setField("context_signal", DeviceOperators.isScreenOn());
        return this.pStream;
    }
}
