package io.github.contextawareness.sensor;

import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.utils.annotations.PSItem;
import io.github.contextawareness.utils.annotations.PSItemField;

/**
 * Ambient relative humidity sensor.
 */
@PSItem
public class RelativeHumidity extends Item {

    /**
     * Ambient relative humidity. Unit: %.
     */
    @PSItemField(type = Float.class)
    public static final String HUMIDITY = "humidity";

    RelativeHumidity(float humidity) {
        this.setFieldValue(HUMIDITY, humidity);
    }

    /**
     * Provide a live stream of sensor readings from ambient relative humidity sensor.
     * @return the provider.
     */
    public static PStreamProvider asUpdates(int sensorDelay){
        return new RelativeHumidityUpdatesProvider(sensorDelay);
    }
}
