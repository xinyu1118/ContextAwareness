package io.github.eventawareness.commons.debug;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access debugging functions
 */
@PSOperatorWrapper
public class DebugOperators {
    /**
     * Print the input for debugging.
     *
     * @param <Tin> the input type
     * @return the function
     */
    public static <Tin> Function<Tin, Void> debug() {
        return new DebugPrintOperator<>();
    }

    /**
     * Log the input and return as original.
     *
     * @param logTag the log tag to use in logging
     * @param <T> the input/output type
     * @return the function
     */
    public static <T> Function<T, T> logAs(String logTag) {
        return new EchoOperator<>(logTag, false);
    }

    /**
     * Log the input in socket and return as original.
     *
     * @param logTag the log tag to use in logging
     * @param <T> the input/output type
     * @return the function
     */
    public static <T> Function<T, T> logOverSocket(String logTag) {
        return new EchoOperator<>(logTag, true);
    }
}
