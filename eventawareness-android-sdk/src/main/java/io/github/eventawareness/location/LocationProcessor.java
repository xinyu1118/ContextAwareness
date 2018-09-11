package io.github.eventawareness.location;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Process the location field in an item.
 * @param <Tout> the latlon type
 */
abstract class LocationProcessor<Tout> extends ItemOperator<Tout> {
    private final String latLonField;

    LocationProcessor(String latLonField) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.addParameters(this.latLonField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        LatLon latLon = input.getValueByField(this.latLonField);
        return this.processLocation(latLon);
    }

    protected abstract Tout processLocation(LatLon latLon);
}
