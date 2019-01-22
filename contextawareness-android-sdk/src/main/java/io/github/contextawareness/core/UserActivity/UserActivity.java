package io.github.contextawareness.core.UserActivity;


import io.github.contextawareness.core.Contexts;

/**
 * A helper class used to sense user activity related contexts.
 */
public class UserActivity {

    /**
     * Monitor user activities including in_vehicle, on_bicycle, on_foot, running, still, tilting, walking.
     * @param queryActivity the query activity, could be DetectedActivity.IN_VEHICLE, DetectedActivity.ON_BICYCLE,
     *                      DetectedActivity.ON_FOOT, DetectedActivity.RUNNING, DetectedActivity.STILL,
     *                      DetectedActivity.TILTING, DetectedActivity.WALKING:
     * @return Contexts provider
     */
    public static Contexts ActivityRecognition(int queryActivity) {
        return new UserActivityRecognition(queryActivity);
    }

}

