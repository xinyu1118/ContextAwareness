package io.github.contextawareness.location;


import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

/**
 * Get the precise location.
 */
public class LocationCoordinateGetter extends ItemOperator<LatLon>{
    private final String latLonField;

    LocationCoordinateGetter(String latLonField) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.addParameters(latLonField);
    }

    @Override
    public LatLon apply(UQI uqi, Item input) {
        return input.getValueByField(this.latLonField);
    }
}
