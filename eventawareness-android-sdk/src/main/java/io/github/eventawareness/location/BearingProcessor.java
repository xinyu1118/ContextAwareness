package io.github.eventawareness.location;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Process the location bearing field in an item.
 * @param <Tout> the bearing type
 */
abstract class BearingProcessor<Tout> extends ItemOperator<Tout> {
    private final String bearingField;

    BearingProcessor(String bearingField) {
        this.bearingField = Assertions.notNull("bearingField", bearingField);
        this.addParameters(this.bearingField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        Float bearing = input.getValueByField(this.bearingField);
        return this.processBearing(bearing);
    }

    protected abstract Tout processBearing(Float bearing);
}