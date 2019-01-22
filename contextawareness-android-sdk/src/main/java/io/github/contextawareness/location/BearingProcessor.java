package io.github.contextawareness.location;


import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

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