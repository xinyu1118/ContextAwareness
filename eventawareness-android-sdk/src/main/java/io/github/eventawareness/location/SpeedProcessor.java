package io.github.eventawareness.location;


import io.github.eventawareness.commons.ItemOperator;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;

/**
 * Process the location speed field in an item.
 * @param <Tout> the speed type
 */
abstract class SpeedProcessor<Tout> extends ItemOperator<Tout> {
    private final String speedField;

    SpeedProcessor(String speedField) {
        this.speedField = Assertions.notNull("speedField", speedField);
        this.addParameters(this.speedField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        Float speed = input.getValueByField(this.speedField);
        return this.processSpeed(speed);
    }

    protected abstract Tout processSpeed(Float speed);
}