package io.github.eventawareness.sensor;

import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.PStreamProvider;
import io.github.eventawareness.utils.annotations.PSItem;
import io.github.eventawareness.utils.annotations.PSItemField;

/**
 * Air pressure environment sensor.
 */
@PSItem
public class AirPressure extends Item {

    /**
     * Ambient air pressure. Unit: hPa or mbar.
     */
    @PSItemField(type = Float.class)
    public static final String PRESSURE = "pressure";

    AirPressure(float pressure) {
        this.setFieldValue(PRESSURE, pressure);
    }

    /**
     * Provide a live stream of sensor readings from air pressure sensor.
     * @return the provider.
     */
    public static PStreamProvider asUpdates(int sensorDelay){
        return new AirPressureUpdatesProvider(sensorDelay);
    }
}
