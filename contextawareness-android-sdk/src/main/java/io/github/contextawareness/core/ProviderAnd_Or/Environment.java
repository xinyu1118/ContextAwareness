package io.github.contextawareness.core.ProviderAnd_Or;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.PStreamProvider;
import io.github.contextawareness.core.purposes.Purpose;
import io.github.contextawareness.utils.annotations.PSItemField;
import io.github.contextawareness.core.Function;

/**
 * A motion event generated with Google Awareness API
 * TODO clarify this class before making this public
 */

public class Environment extends Item{
    /**
     * The timestamp of the event
     */
    @PSItemField(type = Long.class)
    private static final String TIMESTAMP = "timestamp";

    /**
     * The motion type, which is the return value of google Awareness API `FenceState.getFenceKey()`
     */
    private static final String MOTION_TYPE ="motion_type";

    Environment(long timestamp, String motionType){
        this.setFieldValue(TIMESTAMP, timestamp);                        //Assign value to each of the member variable
        this.setFieldValue(MOTION_TYPE, motionType);
    }

    /**
     * Provide a live stream of AwarenessMotion items.
     *
     * @return the function
     */
    // @RequiresPermission(value = "com.google.android.gms.permission.ACTIVITY_RECOGNITION")
    public static PStreamProvider asAnd(LinkedList<EnvironmentFactor> factors) {
        return new EnvironmentAndProvider(factors);
    }
}
