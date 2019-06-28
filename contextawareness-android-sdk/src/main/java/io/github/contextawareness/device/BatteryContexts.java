package io.github.contextawareness.device;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.PStream;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.core.purposes.Purpose;

class BatteryContexts extends Contexts {
    private PStreamProvider pStreamProvider;
    private PStream pStream;

    private float threshold;
    private float lowerBound;
    private float upperBound;
    private boolean flag = false;

    BatteryContexts(float threshold) {
        this.pStreamProvider = this.getProvider();
        this.threshold = threshold;
    }

    BatteryContexts(float lowerBound, float upperBound) {
        this.pStreamProvider = this.getProvider();
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.flag = true;
    }

    @Override
    public PStreamProvider getProvider() {
        return new DeviceStateUpdatesProvider(3000, DeviceState.Masks.BATTERY_LEVEL);
    }

    @Override
    public PStream contextJudgment(UQI uqi) {
        if (!flag) {
            pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("battery lower than a threshold"))
                    .setField("context_signal", DeviceOperators.isLowBattery(this.threshold));
        } else {
            pStream = uqi.getData(pStreamProvider, Purpose.UTILITY("battery in a section"))
                    .setField("context_signal", DeviceOperators.isLowBattery(this.lowerBound, this.upperBound));
        }

        return this.pStream;
    }
}
