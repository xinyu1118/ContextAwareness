package io.github.contextawareness.core.PersonalData;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import java.util.Date;
import java.util.List;

import io.github.contextawareness.communication.PhonecallReceiver;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

import static io.github.contextawareness.utils.CommunicationUtils.normalizePhoneNumber;

/**
 * Monitor the source of an incoming call, a certain phone number or a list.
 */
public class CallerFrom extends Contexts {

    private String phoneNumber;
    private List<String> queryList;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;

    int recurrences;
    String caller;

    private CallReceiver callReceiver;

    public CallerFrom(String phoneNumber) {
        recurrences = 0;
        this.phoneNumber = phoneNumber;
        this.addRequiredPermissions(Manifest.permission.PROCESS_OUTGOING_CALLS);
        this.addRequiredPermissions(Manifest.permission.READ_PHONE_STATE);
    }

    public CallerFrom(List<String> queryList) {
        recurrences = 0;
        this.queryList = queryList;
        this.addRequiredPermissions(Manifest.permission.PROCESS_OUTGOING_CALLS);
        this.addRequiredPermissions(Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    public void setListeningParameters(UQI uqi, int numOfRecurrences, long interval) {
        this.uqi = uqi;
        this.numOfRecurrences = numOfRecurrences;
        this.interval = interval;
    }

    @Override
    protected void provide() {
        callReceiver = new CallReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        uqi.getContext().registerReceiver(callReceiver, filter);

    }

    public class CallReceiver extends PhonecallReceiver {

        @Override
        protected void onIncomingCallReceived(Context ctx, String number, Date start) {
            caller = normalizePhoneNumber(number);
            if (phoneNumber != null) {
                if (caller.equals(phoneNumber)) {
                    Log.d(Consts.LIB_TAG, "Caller from the phone number.");
                    CallerFrom.this.isContextsAwared = true;
                }
            } else {
                for (String listName : queryList) {
                    if (caller.equals(listName)) {
                        Log.d(Consts.LIB_TAG, "Caller in the list.");
                        CallerFrom.this.isContextsAwared = true;
                        break;
                    }
                }
            }

            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                CallerFrom.this.isCancelled = true;
                uqi.getContext().unregisterReceiver(callReceiver);
            } else {
                CallerFrom.this.output(new SignalItem(System.currentTimeMillis(), CallerFrom.this.isContextsAwared));
                CallerFrom.this.isContextsAwared = false;
            }
        }

        @Override
        protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
        }

        @Override
        protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        }

        @Override
        protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        }

        @Override
        protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        }

        @Override
        protected void onMissedCall(Context ctx, String number, Date start) {
        }

    }
}
