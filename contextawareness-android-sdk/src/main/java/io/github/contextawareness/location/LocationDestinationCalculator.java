package io.github.contextawareness.location;


import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;
import io.github.contextawareness.utils.LocationUtils;

/**
 * Destination arrival checker.
 */
public class LocationDestinationCalculator extends ItemOperator<Boolean> {
    private String latLonField;
    private LatLon destination;

    LocationDestinationCalculator(String latLonField, LatLon destination) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.destination = Assertions.notNull("destination", destination);
        this.addParameters(latLonField, destination);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        LatLon latLon = input.getValueByField(this.latLonField);
        Double distance = LocationUtils.getDistanceBetween(latLon, destination);
        // Error tolerance 10.0 meters
        return (distance <= 10.0)? true: false;
    }
}