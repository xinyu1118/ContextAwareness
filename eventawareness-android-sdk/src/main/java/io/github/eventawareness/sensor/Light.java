package io.github.eventawareness.sensor;

import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.PStreamProvider;
import io.github.eventawareness.utils.annotations.PSItem;
import io.github.eventawareness.utils.annotations.PSItemField;

/**
 * Light environment sensor.
 */
@PSItem
public class Light extends Item {

    /**
     * The light illuminance. Unit: lx.
     */
    @PSItemField(type = Float.class)
    public static final String ILLUMINANCE = "illuminance";

    Light(float illuminance) {
        this.setFieldValue(ILLUMINANCE, illuminance);
    }

    /**
     * Provide a live stream of sensor readings from light sensor.
     * @return the provider.
     */
    public static PStreamProvider asUpdates(int sensorDelay){
        return new LightUpdatesProvider(sensorDelay);
    }
}
