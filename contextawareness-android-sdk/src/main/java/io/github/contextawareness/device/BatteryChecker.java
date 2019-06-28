package io.github.contextawareness.device;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.DeviceUtils;

class BatteryChecker extends Function<Item, Boolean> {
    private float threshold;
    private float lowerBound;
    private float upperBound;
    private boolean flag = false;

   BatteryChecker(float threshold) {
       this.threshold = threshold;
    }

    BatteryChecker(float lowerBound, float upperBound) {
       this.lowerBound = lowerBound;
       this.upperBound = upperBound;
       this.flag = true;
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        float currentBattery = DeviceUtils.getBatteryLevel(uqi.getContext());
        if (!flag) {
            if (currentBattery <= threshold)
                return Boolean.TRUE;
            else
                return Boolean.FALSE;
        } else {
            if (currentBattery >= lowerBound && currentBattery <=upperBound)
                return Boolean.TRUE;
            else
                return Boolean.FALSE;
        }
    }

}
