package io.github.eventawareness.commons.debug;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Logging;

/**
 * Print the item for debugging
 */

final class DebugPrintOperator<Tin> extends Function<Tin, Void> {
    @Override
    public Void apply(UQI uqi, Tin input) {
        String debugMsg;

        if (input instanceof Item) debugMsg = ((Item) input).toDebugString();
        else debugMsg = "" + input;

        Logging.debug(debugMsg);
        return null;
    }

}
