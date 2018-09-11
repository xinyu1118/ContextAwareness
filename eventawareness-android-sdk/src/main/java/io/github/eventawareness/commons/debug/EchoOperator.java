package io.github.eventawareness.commons.debug;

import android.util.Log;

import io.github.eventawareness.core.Function;
import io.github.eventawareness.core.Item;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Assertions;
import io.github.eventawareness.utils.PSDebugSocketServer;

import java.util.Locale;

/**
 * Print the item for debugging
 */

final class EchoOperator<T> extends Function<T, T> {

    private final String logTag;
    private final boolean sendToSocket;

    EchoOperator(String logTag, boolean sendOverSocket) {
        this.logTag = Assertions.notNull("logTag", logTag);
        this.sendToSocket = sendOverSocket;
        if (sendOverSocket) this.addRequiredPermissions(android.Manifest.permission.INTERNET);
    }

    @Override
    public T apply(UQI uqi, T input) {
        String logMsg = "" + input;

        if (sendToSocket) {
            String message = String.format(Locale.getDefault(), "%s >>> %s", this.logTag, logMsg);
            PSDebugSocketServer.v().send(message);
        }
        else {
            Log.d(this.logTag, logMsg);
        }
        return input;
    }

}
