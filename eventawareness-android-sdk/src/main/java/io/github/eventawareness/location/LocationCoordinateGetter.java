package io.github.eventawareness.location;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

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
