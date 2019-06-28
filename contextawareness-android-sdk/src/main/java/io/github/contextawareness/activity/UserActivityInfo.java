package io.github.contextawareness.activity;

import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.utils.annotations.PSItemField;

/**
 * A motion event generated with Google Awareness API
 * TODO clarify this class before making this public
 */

public class UserActivityInfo extends Item {
    /**
     * The timestamp of the event
     */
    @PSItemField(type = Long.class)
    private static final String TIMESTAMP = "timestamp";

    /**
     * The motion type, which is the return value of google Awareness API `FenceState.getFenceKey()`
     */
    private static final String MOTION_TYPE ="motion_type";

    UserActivityInfo(long timestamp, String motionType){
        this.setFieldValue(TIMESTAMP, timestamp);
        this.setFieldValue(MOTION_TYPE, motionType);
    }

    /**
     * Provide a live stream of AwarenessMotion items.
     *
     * @return the function
     */
    // @RequiresPermission(value = "com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    public static PStreamProvider asUpdates(long interval) {
        return new UserActivityInfoProvider(interval);
    }
}
