package io.github.contextawareness.core.PersonalData;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import java.util.Date;

import io.github.contextawareness.communication.PhonecallReceiver;
import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Monitor incoming calls.
 */
public class CallsComingIn extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;
    int recurrences;

    private CallReceiver callReceiver;

    public CallsComingIn() {
        recurrences = 0;
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
            CallsComingIn.this.isContextsAwared = true;
            recurrences++;
            if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                CallsComingIn.this.isCancelled = true;
                uqi.getContext().unregisterReceiver(callReceiver);
            } else {
                Log.d(Consts.LIB_TAG, "New calls arrived.");
                CallsComingIn.this.output(new SignalItem(System.currentTimeMillis(), CallsComingIn.this.isContextsAwared));
                CallsComingIn.this.isContextsAwared = false;
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
