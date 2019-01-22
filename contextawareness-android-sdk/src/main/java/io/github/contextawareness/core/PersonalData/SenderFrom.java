package io.github.contextawareness.core.PersonalData;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.List;

import io.github.contextawareness.core.Contexts;
import io.github.contextawareness.core.SignalItem;
import io.github.contextawareness.core.UQI;
import io.github.contextawareness.utils.CommunicationUtils;
import io.github.contextawareness.utils.Consts;

/**
 * Monitor the source of an incoming message, a certain phone number or a list.
 */
public class SenderFrom extends Contexts {

    private String phoneNumber;
    private List<String> queryList;

    private UQI uqi;
    private int numOfRecurrences;
    private long interval;
    int recurrences;

    private SMSReceiver smsReceiver;

    public SenderFrom(String phoneNumber) {
        recurrences = 0;
        this.phoneNumber = phoneNumber;
        this.addRequiredPermissions(Manifest.permission.RECEIVE_SMS);
    }

    public SenderFrom(List<String> queryList) {
        recurrences = 0;
        this.queryList = queryList;
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
//        final SmsManager sms = SmsManager.getDefault();
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                // Get the SMS message received
                final Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    // A PDU is a "protocol data unit". This is the industrial standard for SMS message
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    if (pdusObj == null) return;

                    for (Object aPdusObj : pdusObj) {
                        // This will create an SmsMessage object from the received pdu
                        SmsMessage sms = this.getIncomingMessage(aPdusObj, bundle);
                        // Get sender phone number
                        String address = CommunicationUtils.normalizePhoneNumber(sms.getDisplayOriginatingAddress());
                        String body = sms.getDisplayMessageBody();

                        if (phoneNumber != null) {
                            if (address.equals(phoneNumber)) {
                                Log.d(Consts.LIB_TAG, "Sender from the phone number.");
                                SenderFrom.this.isContextsAwared = true;
                            }
                        } else {
                            for (String listName : queryList) {
                                if (address.equals(listName)) {
                                    Log.d(Consts.LIB_TAG, "Sender in the list.");
                                    SenderFrom.this.isContextsAwared = true;
                                    break;
                                }
                            }
                        }
                        recurrences++;
                        if (numOfRecurrences != Contexts.AlwaysRepeat && recurrences > numOfRecurrences) {
                            Log.d(Consts.LIB_TAG, "Reaching the number of recurrences, end outputting.");
                            SenderFrom.this.isCancelled = true;
                            uqi.getContext().unregisterReceiver(smsReceiver);
                        } else {
                            SenderFrom.this.output(new SignalItem(System.currentTimeMillis(), SenderFrom.this.isContextsAwared));
                            SenderFrom.this.isContextsAwared = false;
                        }
                    }
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
