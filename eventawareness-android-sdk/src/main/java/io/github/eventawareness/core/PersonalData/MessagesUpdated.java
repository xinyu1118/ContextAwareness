package io.github.eventawareness.core.PersonalData;

import android.Manifest;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import io.github.eventawareness.core.Contexts;
import io.github.eventawareness.core.SignalItem;
import io.github.eventawareness.core.UQI;
import io.github.eventawareness.utils.Consts;

public class MessagesUpdated extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    int recurrences = 0;
    MessagesObserver messagesObserver = new MessagesObserver(new Handler());

    public MessagesUpdated() {
        recurrences = 0;
        this.addRequiredPermissions(Manifest.permission.RECEIVE_SMS);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        uqi.getContext().getContentResolver().registerContentObserver(Uri.parse("content://sms"),true, messagesObserver);
    }

    /**
     * Inner class extends from ContentObserver, and overrides onChange() method
     * used to monitor message changes.
     */
    private final class MessagesObserver extends ContentObserver {
        public MessagesObserver(Handler handler){
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                uqi.getContext().getContentResolver().unregisterContentObserver(messagesObserver);
                MessagesUpdated.this.isCancelled = true;
                MessagesUpdated.this.finish();
            } else {
                Log.d(Consts.LIB_TAG, "Messages updated.");
                MessagesUpdated.this.isContextsAwared = true;
                MessagesUpdated.this.output(new SignalItem(System.currentTimeMillis(), MessagesUpdated.this.isContextsAwared));
            }
            MessagesUpdated.this.isContextsAwared = false;
        }
    }
}
