package io.github.contextawareness.location;


import android.content.Context;

import io.github.contextawareness.commons.ItemOperator;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Assertions;

/**
 * Process the location city field in an item.
 * @param <Tout> the city type
 */
abstract class CityProcessor<Tout> extends ItemOperator<Tout> {
    private final String latLonField;

    CityProcessor(String latLonField) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.addParameters(this.latLonField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        LatLon latLon = input.getValueByField(this.latLonField);
        Context context = uqi.getContext();
        return this.processCity(context, latLon);
    }

    protected abstract Tout processCity(Context context, LatLon latLon);
}
