package io.github.contextawareness.core.PersonalData;

import android.Manifest;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Monitor contacts updated, including adding, deleting, modifying.
 */
public class ContactsUpdated extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    int recurrences = 0;
    ContactsObserver contactsObserver = new ContactsObserver(new Handler());

    public ContactsUpdated() {
        recurrences = 0;
        this.addRequiredPermissions(Manifest.permission.READ_CONTACTS);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        uqi.getContext().getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI,true, contactsObserver);
    }

    /**
     * Inner class extends from ContentObserver, and overrides onChange() method
     * used to monitor contact lists changes.
     */
    private final class ContactsObserver extends ContentObserver {
        public ContactsObserver(Handler handler){
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                uqi.getContext().getContentResolver().unregisterContentObserver(contactsObserver);
                ContactsUpdated.this.isCancelled = true;
                ContactsUpdated.this.finish();
            } else {
                Log.d(Consts.LIB_TAG, "Contacts updated.");
                ContactsUpdated.this.isContextsAwared = true;
                ContactsUpdated.this.output(new SignalItem(System.currentTimeMillis(), ContactsUpdated.this.isContextsAwared));
            }
            ContactsUpdated.this.isContextsAwared = false;
        }
    }
}
