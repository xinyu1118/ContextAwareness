package io.github.contextawareness.commons.debug;

import io.github.contextawareness.core.Function;
import io.github.contextawareness.core.Item;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Logging;

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
