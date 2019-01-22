package io.github.contextawareness.core.PersonalData;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.Consts;

/**
 * Monitor incoming messages.
 */
public class MessagesComingIn extends Contexts {

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;
    int recurrences;

    private SMSReceiver smsReceiver;

    public MessagesComingIn() {
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
        smsReceiver = new SMSReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        uqi.getContext().registerReceiver(smsReceiver, filter);
    }

    public class SMSReceiver extends BroadcastReceiver {
        final SmsManager sms = SmsManager.getDefault();
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                // Get the SMS message received
                MessagesComingIn.this.isContextsAwared = true;
                recurrences++;
                if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                    Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                    MessagesComingIn.this.isCancelled = true;
                    uqi.getContext().unregisterReceiver(smsReceiver);
                } else {
                    Log.d(Consts.LIB_TAG, "New messages arrived.");
                    MessagesComingIn.this.output(new SignalItem(System.currentTimeMillis(), MessagesComingIn.this.isContextsAwared));
                    MessagesComingIn.this.isContextsAwared = false;
                }
            }
        }

        private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
            SmsMessage currentSMS;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
            } else {
                currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
            }
            return currentSMS;
        }
    }

}
