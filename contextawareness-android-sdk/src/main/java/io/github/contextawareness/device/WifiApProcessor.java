package io.github.contextawareness.device;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

/**
 * Process the location field in an item.
 */
abstract class WifiApProcessor<Tout> extends ItemOperator<Tout> {

    private final String wifiApField;

    WifiApProcessor(String wifiApField) {
        this.wifiApField = Assertions.notNull("wifiApField", wifiApField);
        this.addParameters(this.wifiApField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        String wifiApFieldValue = input.getValueByField(this.wifiApField);
        return this.processWifiAp(wifiApFieldValue);
    }

    protected abstract Tout processWifiAp(String wifiApFieldValue);
}
