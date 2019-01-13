package io.github.eventawareness.core.PhoneState;


import io.github.eventawareness.core.Contexts;

/**
 * A helper class used to sense phone state related contexts.
 */
public class PhoneState {

    /**
     * Judge the screen on/off status.
     * @return Contexts provider
     */
    public static Contexts ScreenOn() {
        return new ScreenOn();
    }

    /**
     * Compare the number of running apps with a certain threshold.
     * @param comparisonOperator used to specify the comparison operator,
     *                           could be found in 'Operators' class, e.g. Operators.GTE.
     * @param threshold the compared number
     * @return Contexts provider
     */
    public static Contexts RunningApps(String comparisonOperator, int threshold) {
        return new RunningApps(comparisonOperator, threshold);
    }

    /**
     * Check the current battery level.
     * @param comparisonOperator used to specify the comparison operator,
     *                           could be found in 'Operators' class, e.g. Operators.GTE.
     * @param threshold the compared number
     * @return Contexts provider
     */
    public static Contexts BatteryLevel(String comparisonOperator, int threshold) {
        return new BatteryLevel(comparisonOperator, threshold);
    }

    /**
     * Judge whether the phone is charging.
     * @return Contexts provider
     */
    public static Contexts IsCharging(){
        return new IsCharging();
    }

    /**
     * Judge how the phone is charging.
     * @param chargingMode either IsCharging.USB_Mode, or IsCharging.AC_Mode
     * @return Contexts provider
     */
    public static Contexts IsCharging(int chargingMode) {
        return new IsCharging(chargingMode);
    }

    /**
     * Judge whether the headphones are plugged in.
     * @return Contexts provider
     */
    public static Contexts HeadphonesPlugged() {
        return new HeadphonesPlugged();
    }

    /**
     * Judge whether the phone has connected to nearby WiFi.
     * @return Contexts provider
     */
    public static Contexts WiFiConnected() {
        return new WiFiConnected();
    }

    /**
     * Judge whether phone is proximity to nearby beacons.
     * @return Contexts provider
     */
    public static Contexts ProximityToBeacons() {
        return new ProximityToBeacons();
    }

}
