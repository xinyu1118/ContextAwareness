package io.github.contextawareness.core.EnvironmentVariable;


import java.util.Date;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.utils.Globals;

/**
 * A helper class used to sense environment related contexts.
 */
public class EnvironmentVariable {

    /**
     * Monitor location updates.
     * @return Contexts provider
     */
    public static Contexts LocationUpdated() {
        if (Globals.LocationConfig.useGoogleService)
            return new GoogleLocationUpdated();
        else
            return new LocationUpdated();
    }

    /**
     * Judge whether the location is in an area which is specified by latitude, longitude and radius.
     * @param centerLat the center latitude of the area
     * @param centerLng the center longitude of the area
     * @param radius the radius of the the area
     * @return Contexts provider
     */
    public static Contexts LocationInArea(double centerLat, double centerLng, double radius) {
        if (Globals.LocationConfig.useGoogleService)
            return new GoogleLocationInArea(centerLat, centerLng, radius);
        else
            return new LocationInArea(centerLat, centerLng, radius);
    }

    /**
     * Judge whether the location is in an area with Google service which is specified by
     * minimum latitude and longitude, maximum latitude and longitude.
     * @param minLat the minimum latitude
     * @param minLng the minimum longitude
     * @param maxLat the maximum latitude
     * @param maxLng the maximum longitude
     * @return Contexts provider
     */
    public static Contexts LocationInArea(double minLat, double minLng, double maxLat, double maxLng) {
        if (Globals.LocationConfig.useGoogleService)
            return new GoogleLocationInArea(minLat, minLng, maxLat, maxLng);
        else
            return new LocationInArea(minLat, minLng, maxLat, maxLng);
    }

    /**
     * Judge whether the location is in a place.
     * @param placeName the place name
     * @param radius the maximum radius of the place
     * @return Contexts provider
     */
    public static Contexts LocationInArea(String placeName, double radius) {
        if (Globals.LocationConfig.useGoogleService)
            return new GoogleLocationInArea(placeName, radius);
        else
            return new LocationInArea(placeName, radius);
    }

    /**
     * Check the current date.
     * @param queryMode the query mode for time related contexts,
     *                  TimeQuery.Query_Date for this case
     * @param date the query date, pattern is "yyyy-MM-dd kk:mm:ss"
     * @return Contexts provider
     */
    public static Contexts TimeQuery(int queryMode, Date date) {
        return new TimeQuery(queryMode, date);
    }

    /**
     * Check the current time.
     * @param queryMode the query mode for time related contexts, i.e.
     *                  TimeQuery.Query_Day_Of_Week, TimeQuery.Query_Hour_Of_Day
     * @param time the query day of week (starting from Sunday) or
     *             the query hour of day (24-hour system)
     * @return Contexts provider
     */
    public static Contexts TimeQuery(int queryMode, int time) {
        return new TimeQuery(queryMode, time);
    }

    /**
     * Check whether the current time is in a period.
     * @param queryMode the query mode for time related contexts,
     *                  TimeQuery.Query_Hour_In_Section for this case
     * @param startHour the query starting hour
     * @param endHour the query ending hour
     * @return Contexts provider
     */
    public static Contexts TimeQuery(int queryMode, int startHour, int endHour) {
        return new TimeQuery(queryMode, startHour, endHour);
    }

    /**
     * Monitor the speed in m/s.
     * @param comparisonOperator the comparison operator,
     *                           could be found in 'Operators' class, e.g. Operators.GTE.
     * @param threshold the compared number
     * @return Contexts provider
     */
    public static Contexts SpeedLevel(String comparisonOperator, float threshold) {
        return new SpeedLevel(comparisonOperator, threshold);
    }

    /**
     * Compare the current temperature with a threshold in centigrade.
     * @param comparisonOperator the comparison operator,
     *                           could be found in 'Operators' class, e.g. Operators.GTE.
     * @param threshold the compared number
     * @return Contexts provider
     */
    public static Contexts TemperatureLevel(String comparisonOperator, float threshold) {
        return new TemperatureLevel(comparisonOperator, threshold);
    }

}
