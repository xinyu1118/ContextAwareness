package io.github.contextawareness.location;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

public class LocationStatusChecker extends ItemOperator<Boolean> {
    private final String latLonField;
    private LatLon lastLatLon = null;

    LocationStatusChecker(String latLonField) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.addParameters(latLonField);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        LatLon currentLatLon = input.getValueByField(this.latLonField);
        if (!currentLatLon.equals(lastLatLon)) {
            lastLatLon = currentLatLon;
            return Boolean.TRUE;
        } else {
            lastLatLon = currentLatLon;
            return Boolean.FALSE;
        }
    }

}
